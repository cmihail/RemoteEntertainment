// TODO

#include "../common/Server.h"

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

socket_descriptor_t Server::init() {
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

socket_descriptor_t Server::newConnection(socket_descriptor_t listenSocket) {
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

void Server::endConnection(socket_descriptor_t socketDescriptor) {
  close(socketDescriptor);
  currectNumOfClients--;
}

Message Server::receiveMessage(socket_descriptor_t socketDescriptor) {
  Message message(2000); // TODO(cmihail): change 2000
  int n = recv(socketDescriptor, message.getContent(), message.getLength(), 0);
  assert(n >= 0);

  if (n == 0) {
    message = Message(0); // TODO(cmihail): check if this produces memory leaks
  }
  return message;
}

void Server::sendMessage(socket_descriptor_t socketDescriptor, Message & message) {
  // TODO(cmihail): check value returned by <send>
  assert(send(socketDescriptor, message.getContent(), message.getLength(), 0) >= 0);
}
