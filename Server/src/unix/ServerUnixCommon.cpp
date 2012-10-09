/*
 * ServerUnixCommon.cpp
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#include "ServerUnixCommon.h"

#include <cassert>
#include <cstdlib>
#include <cstring>

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <sys/types.h>

#include <sys/stat.h>
#include <fcntl.h>
#include <dirent.h>
#include <netdb.h>

int currectNumOfClients = 0;
int maxNumOfClients = 0;

int serverUnixCommon_init(int serverPort, int maximumNumOfClients) {
  maxNumOfClients = maximumNumOfClients;
  struct sockaddr_in serverAddr;

  // Create socket.
  int socketFileDescriptor = socket(AF_INET, SOCK_STREAM, 0);
  assert(socketFileDescriptor >= 0);

  // Create server.
  memset((char *) &serverAddr, 0, sizeof(serverAddr));
  serverAddr.sin_family = AF_INET;
  serverAddr.sin_addr.s_addr = INADDR_ANY; // Use localhost public IP.
  serverAddr.sin_port = htons(serverPort);

  // Bind the socket to the server.
  assert(bind(socketFileDescriptor, (struct sockaddr *) &serverAddr,
      sizeof(struct sockaddr)) >= 0);

  // Socket used for listening.
  listen(socketFileDescriptor, maxNumOfClients);
  return socketFileDescriptor;
}

int serverUnixCommon_newConnection(int listenSocket) {
  if (currectNumOfClients == maxNumOfClients) {
    return -1;
  }

  struct sockaddr_in clientAddr;
  int clientLength = sizeof(clientAddr);

  // Accept new connection.
  int newSocketFileDescriptor = accept(listenSocket,
      (struct sockaddr *) &clientAddr, (socklen_t *) &clientLength);
  assert(newSocketFileDescriptor != -1);

  currectNumOfClients++;
  return newSocketFileDescriptor;
}

void serverUnixCommon_endConnection(int socket) {
  close(socket);
  currectNumOfClients--;
}

Message serverUnixCommon_receive(int socket) {
  Message message(2000); // TODO(cmihail): change 2000
  int n = recv(socket, message.getContent(), message.getLength(), 0);
  assert(n >= 0);

  if (n == 0) {
    message = Message(0); // TODO(cmihail): check if this produces memory leaks
  }
  return message;
}

void serverUnixCommon_send(int socket, Message & message) {
  // TODO(cmihail): check value returned by <send>
  assert(send(socket, message.getContent(), message.getLength(), 0) >= 0);
}
