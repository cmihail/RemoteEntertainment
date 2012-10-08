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

#include <google/protobuf/io/zero_copy_stream_impl.h>

#include <cassert> // TODO(cmihail): not sure about this cassert, maybe a logger instead
#include <cstdlib>
#include <iostream>  // TODO(cmihail): use logger instead
#include <map>

#include <fcntl.h>
#include <sys/socket.h>

#define MAX_NUM_OF_CLIENTS 5

using namespace std;

unsigned int maxNumOfEvents;

socket_descriptor_t listenSocket;
map<socket_descriptor_t, Client> clientsMap;

EventListener * eventListener;

Server::Server(int serverPort) {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  // Number of clients for read events on sockets + a server socket listener.
  maxNumOfEvents = MAX_NUM_OF_CLIENTS + 1;

  // Init server and event listener.
  listenSocket = serverUnixCommon_init(serverPort, MAX_NUM_OF_CLIENTS);
  eventListener = new EventListener(maxNumOfEvents);
  // Add event for the server socket used for listening incoming connections.
  eventListener->addEvent(listenSocket);
}

Server::~Server() {
  delete eventListener;
}

static void registerNewClient(int listenSocket) {
//  if (currectNumOfChangeEvents == maxNumOfEvents) { TODO(cmihail): add this to addEvent
//    cout << "Maxmum number of clients received" << endl;
//    return;
//  }

  // Add read event for the newly created socket.;
  int clientSocket = serverUnixCommon_newConnection(listenSocket);
  eventListener->addEvent(clientSocket);

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
  // Receive command from server.
  int BUFFER_SIZE = 2000; // TODO(cmihail): change 2000
  char * dataBuffer = new char[BUFFER_SIZE];
  memset(dataBuffer, 0, BUFFER_SIZE);
  int n = recv(clientSocket, dataBuffer, BUFFER_SIZE, 0);
  assert(n >= 0);

  // Check if connection with client has ended.
  if (n == 0) {
    cout << "[Server] Connection ended\n";
    eventListener->deleteEvent(clientSocket);
    serverUnixCommon_endConnection(clientSocket);
    clientsMap.erase(clientSocket);
    return;
  }

  PlayerCommand * playerCommand = new PlayerCommand(string(dataBuffer));
  printCommand(playerCommand);

  // Send command to all other clients.
  map<socket_descriptor_t, Client>::iterator it = clientsMap.begin();
  map<socket_descriptor_t, Client>::iterator itEnd = clientsMap.end();
  string codedBuffer = playerCommand->toCodedBuffer();
  for (; it != itEnd; it++) {
    if (it->first != clientSocket) {
      // TODO(cmihail): check value returned by <send>
      assert(send(it->first, codedBuffer.c_str(), codedBuffer.length(), 0) >= 0);
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
