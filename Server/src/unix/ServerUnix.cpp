/*
 * ServerUnix.cpp
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail
 */

#include "../common/Server.h" // TODO(cmihail): maybe to this using -I at compilation
#include "../common/Client.h"
#include "../common/proto/player.pb.h"
#include "../common/proto/PlayerCommand.h"
#include "ServerUnixCommon.h"
#include "EventListener.h"
#include "../common/Message.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#include <cassert> // TODO(cmihail): not sure about this cassert, maybe a logger instead
#include <cstdlib>
#include <iostream>  // TODO(cmihail): use logger instead
#include <map>

#define MAX_NUM_OF_CLIENTS 5

using namespace std;

int listenSocket;

EventListener * eventListener;
map<socket_descriptor_t, Client> clientsMap;

Server::Server(int serverPort) {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  // Init server and event listener.
  listenSocket = serverUnixCommon_init(serverPort, MAX_NUM_OF_CLIENTS);
  eventListener = new EventListener(MAX_NUM_OF_CLIENTS + 1);

  // Add event for the server socket used for listening incoming connections.
  assert(eventListener->addEvent(listenSocket) == true);
}

Server::~Server() {
  delete eventListener;
}

static void registerNewClient(int listenSocket) {
  // Create new socket for the new connection and add read event for it.
  int clientSocket = serverUnixCommon_newConnection(listenSocket);
  if (clientSocket == -1) {
    cout << "Maxmum number of clients received" << endl;
    // TODO(cmihail): alternative for workaround and must be tested more
    eventListener->deleteEvent(listenSocket);
    eventListener->addEvent(listenSocket);
    return;
  }

  assert(eventListener->addEvent(clientSocket) == true);
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

static void receiveCommand(socket_descriptor_t clientSocket) {
  // Receive data from server.
  Message inputMessage = serverUnixCommon_receive(clientSocket);

  // Check if connection with client has ended.
  if (!inputMessage.hasContent()) {
    cout << "[Server] Connection ended\n";
    eventListener->deleteEvent(clientSocket);
    serverUnixCommon_endConnection(clientSocket);
    clientsMap.erase(clientSocket);
    return;
  }

  PlayerCommand * playerCommand = new PlayerCommand(string(inputMessage.getContent()));
  printCommand(playerCommand);

  // Send command to all other clients.
  map<socket_descriptor_t, Client>::iterator it = clientsMap.begin();
  map<socket_descriptor_t, Client>::iterator itEnd = clientsMap.end();
  string codedBuffer = playerCommand->toCodedBuffer();
  Message outputMessage(codedBuffer.length()); // TODO(cmihail): length calculated twice
  outputMessage.setContent(codedBuffer.c_str(), codedBuffer.length());
  for (; it != itEnd; it++) {
    if (it->first != clientSocket) {
      serverUnixCommon_send(it->first, outputMessage);
    }
  }

  delete playerCommand;
}

void Server::run() {
  while(true) {
    int numOfTriggeredEvents = eventListener->checkEvents();
    assert(numOfTriggeredEvents >= 0);
    if (numOfTriggeredEvents == 0) {
      cout << "No events\n"; // TODO(cmihail): log and another behavior
      _exit(EXIT_FAILURE);
    }

    // Get event type based on
    for (int i = 0; i < numOfTriggeredEvents; i++) {
      int descriptor = eventListener->getDescriptor(i);
      assert(descriptor != -1);

      // Receive new connection.
      if (listenSocket == descriptor) {
        cout << "[SERVER] New Client\n";
        registerNewClient(listenSocket);
        continue;
      }

      // Receive commands from clients.
      map<socket_descriptor_t, Client>::iterator it = clientsMap.find(descriptor);
      if (it != clientsMap.end()) {
        cout << "[SERVER] Command received\n";
        receiveCommand(descriptor);
        continue;
      } else {
        // TODO
      }
    }
  }
}
