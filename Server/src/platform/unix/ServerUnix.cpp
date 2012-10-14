/*
 * ServerUnix.cpp
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "Logger.h"
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
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>

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

Message Server::receiveMessage(socket_descriptor_t socketDescriptor) {
  Message message(2000); // TODO(cmihail): change 2000
  int n = recv(socketDescriptor, message.getContent(), message.getLength(), 0);
  if (n < 0) {
    stringstream out;
    out << "Problem at receiving data from " << socketDescriptor;
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str()); // TODO(cmihail): maybe not ERROR
  }

  if (n == 0) {
    message = Message(0); // TODO(cmihail): check if this produces memory leaks
  }
  return message;
}

void Server::sendMessage(socket_descriptor_t socketDescriptor, Message & message) {
  // TODO(cmihail): check value returned by <send> to be the same as length
  if (send(socketDescriptor, message.getContent(), message.getLength(), 0) < 0) {
    stringstream out;
    out << "Problem at sending data to " << socketDescriptor;
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str()); // TODO(cmihail): maybe not ERROR
  }
}
