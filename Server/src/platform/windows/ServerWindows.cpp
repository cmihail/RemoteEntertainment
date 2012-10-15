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
