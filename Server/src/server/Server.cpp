/*
 * Server.cpp
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines a generic implementation of the Server.h.
 */

#if defined(__APPLE__) || defined(__linux__)
  #include <sys/socket.h>
  #include <sys/types.h>
#endif

#include "Message.h"
#include "event/EventListener.h"
#include "logger/Logger.h"
#include "proto/player.pb.h"
#include "proto/PlayerCommand.h"
#include "proto/PlayerMessageHeader.h"
#include "server/Client.h"
#include "server/Server.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#include <cstdlib>
#include <map>
#include <sstream>

#include <iostream> // TODO

using namespace std;

int listenSocket;

EventListener * eventListener;
map<socket_descriptor_t, Client> clientsMap;

Server::Server(int serverPort) : serverPort(serverPort) {
  maxNumOfClients = 5;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  // Init server and event listener.
  listenSocket = this->init();
  eventListener = new EventListener(this->maxNumOfClients + 1);

  // Add event for the server socket used for listening incoming connections.
  if (eventListener->addEvent(listenSocket) != true) {
    stringstream out;
    out << "EventListener->addEvent() failed for " << listenSocket;
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str());
  }
}

Server::~Server() {
  delete eventListener;
}

Message * Server::receiveMessage(socket_descriptor_t socketDescriptor) {
  // Error message.
  stringstream out;
  out << "Problem at receiving data from " << socketDescriptor;

  // Get number of bytes that must be read.
  int numOfBytes;
  int n = recv(socketDescriptor, &numOfBytes, 4, 0);
  if (n < 0) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str());
    return new Message();
  }

  // End of connection.
  if (n == 0) {
    return new Message();
  }

  // Get actual message.
  Message * message = new Message(numOfBytes);
  n = recv(socketDescriptor, message->getContent(), message->getLength(), 0);
  if (n <= 0) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str());
    return new Message();
  }

  return message;
}

void Server::sendMessage(socket_descriptor_t socketDescriptor, Message & message) {
  int n = send(socketDescriptor, message.getContent(), message.getLength(), 0);

  Logger::Type type;
  bool hasWarning = false;
  if (n < 0) {
    type = Logger::SEVERE;
    hasWarning = true;
  } else if (n != message.getLength()) {
    type = Logger::WARNING;
    hasWarning = true;
  }

  if (hasWarning) {
    stringstream out;
    out << "Problem at sending data to " << socketDescriptor;
    Logger::print(__FILE__, __LINE__, type, out.str());
  }
}

static void registerNewClient(Server * server, socket_descriptor_t listenSocket);
static PlayerMessageHeader * receiveMessageHeader(Server * server,
    socket_descriptor_t clientSocket);
static void receiveCommand(Server * server, socket_descriptor_t clientSocket);

void Server::run() {
  while(true) {
    list<socket_descriptor_t> triggeredEvents = eventListener->getTriggeredEvents();
    if (triggeredEvents.size() == 0) {
      Logger::print(__FILE__, __LINE__, Logger::SEVERE, "Invalid/No events");
    }

    list<socket_descriptor_t>::iterator it = triggeredEvents.begin();
    list<socket_descriptor_t>::iterator itEnd = triggeredEvents.end();

    for (; it != itEnd; it++) {
      socket_descriptor_t socketDescriptor = *it;

       // Receive new connection.
       if (listenSocket == socketDescriptor) {
         registerNewClient(this, listenSocket);
         continue;
       }

       // Receive commands from clients.
       map<socket_descriptor_t, Client>::iterator it = clientsMap.find(socketDescriptor);
       if (it != clientsMap.end()) {
         PlayerMessageHeader * header = receiveMessageHeader(this, it->first);
         // Receive command from client.
         if (header->getMessageType() == proto::MessageHeader_Type_COMMAND) {
           receiveCommand(this, it->first);
         } else {
           // TODO(cmihail): not severe + other types
           Logger::print(__FILE__, __LINE__, Logger::SEVERE, "Invalid message");
         }
         delete header;
       } else {
         stringstream out;
         out << "Not a valid socket descriptor: " << socketDescriptor;
         Logger::print(__FILE__, __LINE__, Logger::WARNING, out.str());
       }
    }
  }
}

static void registerNewClient(Server * server, socket_descriptor_t listenSocket) {
  // Create new socket for the new connection.
  int clientSocket = server->newConnection(listenSocket);
  if (clientSocket == -1) {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Maximum number of clients received");
    // TODO(cmihail): alternative for workaround and must be tested more
    eventListener->deleteEvent(listenSocket);
    eventListener->addEvent(listenSocket);
    return;
  }

  stringstream out;
  out << "New client " << clientSocket;
  Logger::print(__FILE__, __LINE__, Logger::INFO, out.str());

  // Add read event for the newly created socket.
  if (eventListener->addEvent(clientSocket) != true) {
    out.clear();
    out << "EventListener->addEvent() failed for " << clientSocket;
    Logger::print(__FILE__, __LINE__, Logger::WARNING, out.str());
  }

  clientsMap.insert(pair<socket_descriptor_t, Client>(clientSocket, Client(clientSocket)));
}

// TODO(cmihail): only for dev, it should be send to all other clients
static void printCommand(PlayerCommand * playerCommand) {
  if (playerCommand->getType() == proto::Command::PAUSE) {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Command received: Pause");
  } else if (playerCommand->getType() == proto::Command::PLAY) {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Command received: Play");
  } else  if (playerCommand->getType() == proto::Command::BACKWARD) {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Command received: Rewind");
  } else  if (playerCommand->getType() == proto::Command::SET_VOLUME) {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Command received: Set Volume");
  } else {
    Logger::print(__FILE__, __LINE__, Logger::INFO, "Command received: Another command");
  }
}

static PlayerMessageHeader * receiveMessageHeader(Server * server,
    socket_descriptor_t clientSocket) {
  // Receive message header from client.
  Message * inputMessage = server->receiveMessage(clientSocket);

  // Check if connection with client has ended.
  if (!inputMessage->hasContent()) {
    eventListener->deleteEvent(clientSocket);
    server->endConnection(clientSocket);
    clientsMap.erase(clientSocket);

    stringstream out;
    out << "Connection ended for " << clientSocket;
    Logger::print(__FILE__, __LINE__, Logger::INFO, out.str());
    return NULL;
  }

  // Transform input message into player message header.
  PlayerMessageHeader * header = new PlayerMessageHeader(*inputMessage);
  delete inputMessage;

  return header;
}

static void receiveCommand(Server * server, socket_descriptor_t clientSocket) {
  Message * inputMessage = server->receiveMessage(clientSocket);

  // Check if any command was sent.
  if (!inputMessage->hasContent()) {
    Logger::print(__FILE__, __LINE__, Logger::WARNING, "Didn't receive command from client");
    // TODO(cmihail): delete client
    return;
  }

  // Transform input message into player command.
  PlayerCommand * command = new PlayerCommand(*inputMessage);
  printCommand(command);
  delete inputMessage;

  // Send command to all other clients.
  map<socket_descriptor_t, Client>::iterator it = clientsMap.begin();
  map<socket_descriptor_t, Client>::iterator itEnd = clientsMap.end();
  Message headerOutputMessage =
      PlayerMessageHeader(proto::MessageHeader_Type_COMMAND).toCodedMessage();
  Message commandOutputMessage = command->toCodedMessage();
  for (; it != itEnd; it++) {
    if (it->first != clientSocket) {
      server->sendMessage(it->first, headerOutputMessage);
      server->sendMessage(it->first, commandOutputMessage);
    }
  }

  delete command;
}
