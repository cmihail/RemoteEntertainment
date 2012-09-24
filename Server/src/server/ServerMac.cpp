/*
 * ServerMac.cpp
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#include "ServerCommon.h"
#include "ServerUnixCommon.h"

#include <cassert>
#include <cstdlib>

#include <iostream>  // TODO(cmihail): use logger instead

#include <fcntl.h>
#include <sys/types.h>
#include <sys/event.h>
#include <sys/socket.h>

#define MAX_NUM_OF_CLIENTS 5

using namespace std;

int maxNumOfConnections;
int kqueueFileDescriptor;
int currectNumOfChangeEvents = 0;
struct kevent * changeList;
struct kevent * eventList;

int mediaPlayerSocket; // TODO(cmihail): only for dev

ServerCommon::ServerCommon(int serverPort) {
  maxNumOfConnections = MAX_NUM_OF_CLIENTS + 1; // nubmer of clients + server socket listener

  // Init server.
  listenSocketFileDescriptor = serverUnixCommon_init(serverPort, maxNumOfConnections);

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

static void registerNewClient(int listenSocketFileDescriptor, int maxNumOfConnections) {
  if (currectNumOfChangeEvents == maxNumOfConnections) {
    cout << "Maxmum number of clients received" << endl;
    return;
  }
  int socketFileDescriptor = serverUnixCommon_newConnection(listenSocketFileDescriptor);
  EV_SET(&changeList[currectNumOfChangeEvents], socketFileDescriptor,
      EVFILT_WRITE, EV_ADD | EV_ENABLE, 0, 0, 0);
  // TODO(cmihail): problem with having both EVFILT_READ and EVFILT_WRITE
  // so split them into 2 separate events
  currectNumOfChangeEvents++;

  // TODO(cmihail): create a client list
  mediaPlayerSocket = socketFileDescriptor;
}

void ServerCommon::run() {
  while(true) {
    int n = kevent(kqueueFileDescriptor, changeList, currectNumOfChangeEvents,
        eventList, currectNumOfChangeEvents, NULL);
    assert(n != -1);
    if (n == 0) {
      cout << "No events\n"; // TODO(cmihail): log and another behavior
      _exit(EXIT_FAILURE);
    }

    for (int i = 0; i < currectNumOfChangeEvents; i++) {
//      cout << "[Server] new event " << i << "\n";
      if (eventList[i].ident == listenSocketFileDescriptor &&
          eventList[i].filter == EVFILT_READ) {
        cout << "[SERVER] New Client\n";
        registerNewClient(listenSocketFileDescriptor, maxNumOfConnections);
      }
      else if (eventList[i].ident == STDIN_FILENO && // TODO(cmihail): only for dev
          eventList[i].filter == EVFILT_READ) {
        string command;
        getline(cin, command);
        cout << "[SERVER] Command: " << command << "\n";
        command += "\n";
        assert(send(mediaPlayerSocket, command.c_str(), command.length() + 1, 0) >= 0);
      }
    }
  }
}

ServerCommon::~ServerCommon() {
  delete changeList;
  delete eventList;
}
