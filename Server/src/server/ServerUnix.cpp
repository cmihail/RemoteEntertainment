/*
mak * ServerUnix.cpp
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "logger/Logger.h"
#include "server/Server.h"

#include <cstdlib>
#include <cstring>
#include <sstream>

#include <dirent.h>
#include <fcntl.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/stat.h>

using namespace std;

int currentNumOfClients = 0;

socket_descriptor_t Server::init() {
  struct sockaddr_in serverAddr;

  // Create socket.
  socket_descriptor_t listenSocket = socket(AF_INET, SOCK_STREAM, 0);
  if (listenSocket < 0) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, "Couldn't create server socket");
  }

  // Create server.
  memset((char *) &serverAddr, 0, sizeof(serverAddr));
  serverAddr.sin_family = AF_INET;
  serverAddr.sin_addr.s_addr = INADDR_ANY; // Use localhost public IP.
  serverAddr.sin_port = htons(serverPort);

  // Bind the socket to the server.
  if (bind(listenSocket, (struct sockaddr *) &serverAddr, sizeof(struct sockaddr)) < 0) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, "Couldn't bind server socket");
  }

  // Socket used for listening.
  listen(listenSocket, maxNumOfClients);
  return listenSocket;
}

socket_descriptor_t Server::newConnection(socket_descriptor_t listenSocket) {
  if (currentNumOfClients == maxNumOfClients) {
     return -1;
   }

   struct sockaddr_in clientAddr;
   int clientLength = sizeof(clientAddr);

   // Accept new connection.
   socket_descriptor_t clientSocket = accept(listenSocket,
       (struct sockaddr *) &clientAddr, (socklen_t *) &clientLength);
   if (clientSocket < 0) {
     Logger::print(__FILE__, __LINE__, Logger::WARNING,
         "Couldn't create new socket for new connection");
     return -1;
   }

   currentNumOfClients++;
   return clientSocket;
}

void Server::endConnection(socket_descriptor_t socketDescriptor) {
  close(socketDescriptor);
  currentNumOfClients--;
}
