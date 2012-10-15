/*
 * ServerWindows.cpp
 *
 *  Created on: Oct 13, 2012
 *      Author: Mihail Costea
 */

#include "Logger.h"
#include "server/Server.h"
#include "platform/windows/sock_util.h"

#include <sstream>

using namespace std;

int currentNumOfClients = 0;

socket_descriptor_t Server::init() {
  socket_descriptor_t listenSocket;

  wsa_init();
  listenSocket = tcp_listen_connections(serverPort, maxNumOfClients);

  return listenSocket;
}

socket_descriptor_t Server::newConnection(socket_descriptor_t listenSocket) {
  struct sockaddr_in address;
  int addressLength = sizeof(address);
  socket_descriptor_t clientSocket = accept(listenSocket, (SSA *) &address, &addressLength);
  if (clientSocket == INVALID_SOCKET) {
    Logger::print(__FILE__, __LINE__, Logger::WARNING,
        "Couldn't create new socket for new connection");
  }

  currentNumOfClients++;
  return clientSocket;
}

void Server::endConnection(socket_descriptor_t socketDescriptor) {
  closesocket(socketDescriptor);
  currentNumOfClients--;
}

// TODO(cmihail): common method with unix method (unite them)
Message Server::receiveMessage(socket_descriptor_t socketDescriptor) {
  Message message(2000); // TODO(cmihail): change 2000
  int n = recv(socketDescriptor, message.getContent(), message.getLength(), 0);
  if (n < 0) {
    stringstream out;
    out << "Problem at receiving data from " << socketDescriptor;
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str());
  }

  if (n == 0) {
    message = Message(0); // TODO(cmihail): check if this produces memory leaks
  }
  return message;
}

// TODO(cmihail): common method with unix method (unite them)
void Server::sendMessage(socket_descriptor_t socketDescriptor, Message & message) {
  if (send(socketDescriptor, message.getContent(), message.getLength(), 0) < 0) {
    stringstream out;
    out << "Problem at sending data to " << socketDescriptor;
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, out.str());
  }
}

