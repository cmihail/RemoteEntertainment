/*
 * ServerMac.cpp
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#include "ServerCommon.h"
#include "ServerUnixCommon.h"
#include "../proto/player.pb.h"

#include <cassert>
#include <cstdlib>

#include <iostream>  // TODO(cmihail): use logger instead

#include <fcntl.h>
#include <sys/types.h>
#include <sys/event.h>
#include <sys/socket.h>

#include <google/protobuf/io/zero_copy_stream_impl.h>

#define MAX_NUM_OF_CLIENTS 5

using namespace std;

unsigned int maxNumOfConnections;
int kqueueFileDescriptor;
unsigned int currectNumOfChangeEvents = 0;
struct kevent * changeList;
struct kevent * eventList;

unsigned int mediaPlayerSocket; // TODO(cmihail): only for dev
bool hasClient = false; // TODO(cmihail): delete

ServerCommon::ServerCommon(int serverPort) {
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

  proto::Command * command = new proto::Command();
  if (commandLine.compare("play") == 0) {
    command->set_type(proto::Command_Type_PLAY);
  } else if (commandLine.compare("pause") == 0) {
    command->set_type(proto::Command_Type_PAUSE);
  } else {
    // TODO(cmihail) default behavior
    command->set_type(proto::Command_Type_STOP);
  }

  ////////
  int dataBufferSize = command->ByteSize() + sizeof(dataBufferSize);
  char * dataBuffer = new char[dataBufferSize];

  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(dataBuffer, dataBufferSize);
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(command->ByteSize());
  assert(command->SerializeToCodedStream(codedOutputStream));

  assert(send(mediaPlayerSocket, dataBuffer, dataBufferSize, 0) >= 0);

  delete codedOutputStream;
  delete zeroCopyOutputStream;
  delete dataBuffer;
  delete command;
}

static void receiveCommand() {
  int BUFFER_SIZE = 2000; // TODO(cmihail): change 2000
  char * dataBuffer = new char[BUFFER_SIZE];
  assert(recv(mediaPlayerSocket, dataBuffer, BUFFER_SIZE, 0));

  google::protobuf::io::ZeroCopyInputStream * zeroCopyInputStream =
      new google::protobuf::io::ArrayInputStream(dataBuffer, BUFFER_SIZE);
  google::protobuf::io::CodedInputStream * codedInputStream =
      new google::protobuf::io::CodedInputStream(zeroCopyInputStream);
  google::protobuf::uint32 size;
  codedInputStream->ReadVarint32(&size);
  google::protobuf::io::CodedInputStream::Limit commandLimit =
      codedInputStream->PushLimit(size);

  proto::Command * command = new proto::Command();
  command->ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  ////////
  if (command->type() == command->PAUSE) {
    cout << "[SERVER] Pause\n";
  } else if (command->type() == command->PLAY) {
    cout << "[SERVER] Play\n";
  } else {
    cout << "[SERVER] Other command\n";
  }

  delete command;
  delete codedInputStream;
  delete zeroCopyInputStream;
  delete dataBuffer;
}

void ServerCommon::run() {
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

ServerCommon::~ServerCommon() {
  delete changeList;
  delete eventList;
}
