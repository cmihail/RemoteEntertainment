/*
 * ServerMac.cpp
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#include "../../common/Server.h" // TODO(cmihail): maybe to this using -I at compilation
#include "../../common/proto/player.pb.h"
#include "../../common/proto/PlayerCommand.h"
#include "../ServerUnixCommon.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#include <cassert>
#include <cstdlib>

#include <iostream>  // TODO(cmihail): use logger instead

#include <fcntl.h>
#include <sys/types.h>
#include <sys/event.h>
#include <sys/socket.h>

#define MAX_NUM_OF_CLIENTS 5

using namespace std;

unsigned int maxNumOfConnections;
int kqueueFileDescriptor;
unsigned int currectNumOfChangeEvents = 0;
struct kevent * changeList;
struct kevent * eventList;

unsigned int mediaPlayerSocket; // TODO(cmihail): only for dev
bool hasClient = false; // TODO(cmihail): delete

Server::Server(int serverPort) {
  GOOGLE_PROTOBUF_VERIFY_VERSION; // TODO(cmihail): make this common

  // Number of clients * 2 for read and write on socket and a server socket listener.
  maxNumOfConnections = MAX_NUM_OF_CLIENTS * 2 + 1;

  // Init server.
  listenSocketFileDescriptor = serverUnixCommon_init(serverPort, MAX_NUM_OF_CLIENTS);

  // Create event notifier.
  kqueueFileDescriptor = kqueue();
  assert(kqueueFileDescriptor != -1);
  changeList = new struct kevent[maxNumOfConnections]; // TODO(cmihail): maybe realloc when needed
  eventList = new struct kevent[maxNumOfConnections];

  // Create event for the server socket used for listening incoming connections.
  EV_SET(&changeList[currectNumOfChangeEvents], listenSocketFileDescriptor,
      EVFILT_READ, EV_ADD | EV_ENABLE, 0, 0, 0);
  currectNumOfChangeEvents = 1;

  // TODO(cmihail): register STDIN, only for dev
  EV_SET(&changeList[currectNumOfChangeEvents], STDIN_FILENO, EVFILT_READ,
      EV_ADD | EV_ENABLE, 0, 0, 0);
  currectNumOfChangeEvents++;
}

static void registerNewClient(int listenSocketFileDescriptor) {
  if (currectNumOfChangeEvents == maxNumOfConnections) {
    cout << "Maxmum number of clients received" << endl;
    return;
  }

  // Add both read and write events for the newly created socket.
  int socketFileDescriptor = serverUnixCommon_newConnection(listenSocketFileDescriptor);
  EV_SET(&changeList[currectNumOfChangeEvents], socketFileDescriptor,
      EVFILT_READ, EV_ADD | EV_ENABLE, 0, 0, 0);
  currectNumOfChangeEvents++;
  EV_SET(&changeList[currectNumOfChangeEvents], socketFileDescriptor,
      EVFILT_WRITE, EV_ADD | EV_ENABLE, 0, 0, 0); // TODO(cmihail): not sure if needed
  currectNumOfChangeEvents++;

  // TODO(cmihail): create a client list
  mediaPlayerSocket = socketFileDescriptor;
  hasClient = true;
}

static void stdinDev() { // TODO(cmihail): only for dev
  string commandLine;
  getline(cin, commandLine);
  cout << "[SERVER] Command: " << commandLine << "\n";

  // Create command from STDIN.
  PlayerCommand * playerCommand;
  if (commandLine.compare("play") == 0) {
    playerCommand = new PlayerCommand(proto::Command_Type_PLAY);
  } else if (commandLine.compare("pause") == 0) {
    playerCommand = new PlayerCommand(proto::Command_Type_PAUSE);
  } else {
    // TODO(cmihail) default behavior -> add NONE to enum
    playerCommand = new PlayerCommand(proto::Command_Type_STOP);
  }

  // Send the command to the player.
  string codedBuffer = playerCommand->toCodedBuffer();
  assert(send(mediaPlayerSocket, codedBuffer.c_str(), codedBuffer.length(), 0) >= 0);
  delete playerCommand;
}

static void receiveCommand() {
  // Receive command from server.
  int BUFFER_SIZE = 2000; // TODO(cmihail): change 2000
  char * dataBuffer = new char[BUFFER_SIZE];
  memset(dataBuffer, 0, BUFFER_SIZE);
  assert(recv(mediaPlayerSocket, dataBuffer, BUFFER_SIZE, 0));
  // TODO(cmihail): see if string(dataBuffer) can produce problems
  PlayerCommand * playerCommand = new PlayerCommand(string(dataBuffer));

  // TODO(cmihail): only for dev, it should be send to all other clients
  if (playerCommand->getType() == proto::Command::PAUSE) {
    cout << "[SERVER] Pause\n";
  } else if (playerCommand->getType() == proto::Command::PLAY) {
    cout << "[SERVER] Play\n";
  } else {
    cout << "[SERVER] Other command\n";
  }

  delete playerCommand;
}

void Server::run() {
  while(true) {
    // Retrieve event list.
    int n = kevent(kqueueFileDescriptor, changeList, currectNumOfChangeEvents,
        eventList, currectNumOfChangeEvents, NULL);
    assert(n != -1);
    if (n == 0) {
      cout << "No events\n"; // TODO(cmihail): log and another behavior
      _exit(EXIT_FAILURE);
    }

    // Check the event type and execute correspondent action.
    for (unsigned int i = 0; i < currectNumOfChangeEvents; i++) {
      // Receive new connection.
      if (eventList[i].ident == listenSocketFileDescriptor &&
          eventList[i].filter == EVFILT_READ) {
        cout << "[SERVER] New Client\n";
        registerNewClient(listenSocketFileDescriptor);
        continue;
      }

      // TODO(cmihail): only for dev
      if (eventList[i].ident == STDIN_FILENO && eventList[i].filter == EVFILT_READ) {
        stdinDev();
        continue;
      }

      // TODO(cmihail): Receive commands from clients.
      if (hasClient && eventList[i].ident == mediaPlayerSocket
          && eventList[i].filter == EVFILT_READ) {
        cout << "[SERVER] Command received\n";
        receiveCommand();
        continue;
      }
    }
  }
}

Server::~Server() {
  delete changeList;
  delete eventList;
}
