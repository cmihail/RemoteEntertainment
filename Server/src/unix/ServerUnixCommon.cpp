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

int serverUnixCommon_init(int serverPort, int maxNumOfClients) {
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
  struct sockaddr_in clientAddr;
  int clientLength = sizeof(clientAddr);

  // Accept new connection.
  int newSocketFileDescriptor = accept(listenSocket,
      (struct sockaddr *) &clientAddr, (socklen_t *) &clientLength);
  assert(newSocketFileDescriptor != -1);

  return newSocketFileDescriptor;
}

void serverUnixCommon_endConnection(int socket) {
  close(socket);
}
