/*
 * Server.cpp
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail
 */

#include "Server.h" // TODO(cmihail): maybe to this using -I at compilation
#include "Client.h" // TODO(cmihail): create a new package
#include "../platform/EventListener.h"
#include "../Message.h"
#include "../Logger.h"
#include "../proto/player.pb.h"
#include "../proto/PlayerCommand.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#include <iostream> // TODO(cmihail): delete this in future
#include <cstdlib>
#include <sstream>
#include <map>

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
    Logger::print(__FILE__, __LINE__, Logger::ERROR, out.str());
  }
}

Server::~Server() {
  delete eventListener;
}

static void registerNewClient(Server * server, socket_descriptor_t listenSocket) {
  // Create new socket for the new connection.
  int clientSocket = server->newConnection(listenSocket);
  if (clientSocket == -1) {
    Logger::print(__FILE__, __LINE__, Logger::INFORMATION, "Maximum number of clients received");
    // TODO(cmihail): alternative for workaround and must be tested more
    eventListener->deleteEvent(listenSocket);
    eventListener->addEvent(listenSocket);
    return;
  }

  stringstream out;
  out << "New client " << clientSocket;
  Logger::print(__FILE__, __LINE__, Logger::INFORMATION, out.str());

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
    cout << "[SERVER] Pause\n";
  } else if (playerCommand->getType() == proto::Command::PLAY) {
    cout << "[SERVER] Play\n";
  } else  if (playerCommand->getType() == proto::Command::REWIND) {
    cout << "[SERVER] Rewind\n";
  } else  if (playerCommand->getType() == proto::Command::FAST_FORWARD) {
    cout << "[SERVER] Fast Forward\n";
  } else {
    cout << "[SERVER] Other command\n";
  }
}

static void receiveCommand(Server * server, socket_descriptor_t clientSocket) {
  // Receive data from server.
  Message inputMessage = server->receiveMessage(clientSocket);

  // Check if connection with client has ended.
  if (!inputMessage.hasContent()) {
    stringstream out;
    out << "Connection ended for " << clientSocket;
    Logger::print(__FILE__, __LINE__, Logger::INFORMATION, out.str());
    eventListener->deleteEvent(clientSocket);
    server->endConnection(clientSocket);
    clientsMap.erase(clientSocket);
    return;
  }

  PlayerCommand * playerCommand = new PlayerCommand(inputMessage);
  printCommand(playerCommand);

  // Send command to all other clients.
  map<socket_descriptor_t, Client>::iterator it = clientsMap.begin();
  map<socket_descriptor_t, Client>::iterator itEnd = clientsMap.end();
  Message outputMessage = playerCommand->toCodedMessage();
  for (; it != itEnd; it++) {
    if (it->first != clientSocket) {
      server->sendMessage(it->first, outputMessage);
    }
  }

  delete playerCommand;
}

void Server::run() {
  while(true) {
    int numOfTriggeredEvents = eventListener->checkEvents();
    assert(numOfTriggeredEvents >= 0);
    if (numOfTriggeredEvents == 0) {
      Logger::print(__FILE__, __LINE__, Logger::WARNING, "No events");
    }

    // Get event type based on
    for (int i = 0; i < numOfTriggeredEvents; i++) {
      int descriptor = eventListener->getDescriptor(i);
      assert(descriptor != -1);

      // Receive new connection.
      if (listenSocket == descriptor) {
        registerNewClient(this, listenSocket);
        continue;
      }

      // Receive commands from clients.
      map<socket_descriptor_t, Client>::iterator it = clientsMap.find(descriptor);
      if (it != clientsMap.end()) {
        Logger::print(__FILE__, __LINE__, Logger::INFORMATION, "Command received ");
        receiveCommand(this, descriptor);
        continue;
      } else {
        stringstream out;
        out << "Not a valid socket descriptor: " << descriptor;
        Logger::print(__FILE__, __LINE__, Logger::WARNING, out.str());
      }
    }
  }
}
