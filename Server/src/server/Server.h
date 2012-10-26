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

/**
 * Defines a generic server for all platforms.
 */
class Server {
  int serverPort;
  int maxNumOfClients;
  // TODO(cmihail): platform dependent
  socket_descriptor_t init();

public:
  /**
   * @param serverPort the port used to listen for connections
   */
  Server(int serverPort);

  virtual ~Server();

  /**
   * The infinite loop in which the server listen for connections and communicates with the clients
   */
  void run();

  // TODO(cmihail): platform dependent
  socket_descriptor_t newConnection(socket_descriptor_t listenSocket);
  // TODO(cmihial): platform dependent
  void endConnection(socket_descriptor_t socketDescriptor);

  /**
   * @param socketDescriptor the socket on which the message is received
   * @return the message that contains the transmitted data
   * (it's allocated dynamically so it should be freed when not needed any more)
   */
  Message * receiveMessage(socket_descriptor_t socketDescriptor);

  /**
   * @param socketDescriptor the socket where the message is sent
   * @param message the data that must be sent
   */
  void sendMessage(socket_descriptor_t socketDescriptor, Message & message);
};


#endif /* SERVER_H_ */
