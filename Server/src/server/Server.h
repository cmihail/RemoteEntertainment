/*
 * Server.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef SERVER_H_
#define SERVER_H_

#include "Common.h"
#include "Message.h"

// TODO(cmihail): split server in order to have what is common separate from what is
// platform dependent
class Server {
  int serverPort;
  int maxNumOfClients;
  socket_descriptor_t init();

public:
  Server(int serverPort);
  virtual ~Server();

  void run();

  socket_descriptor_t newConnection(socket_descriptor_t listenSocket);
  void endConnection(socket_descriptor_t socketDescriptor);
  Message receiveMessage(socket_descriptor_t socketDescriptor);
  void sendMessage(socket_descriptor_t socketDescriptor, Message & message);
};


#endif /* SERVER_H_ */
