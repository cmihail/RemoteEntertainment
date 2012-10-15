/*
 * Client.h
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef CLIENT_H
#define CLIENT_H

#include "Common.h"

/**
 * Defines the client information.
 */
class Client {
  socket_descriptor_t socketDescriptor;

public:
  /**
   * @param socketDescriptor the socket used to connect the server with the client
   */
  Client(socket_descriptor_t socketDescriptor);

  /**
   * @return the socket used to connect the server with the client
   */
  socket_descriptor_t getSocketDescriptor();
};

#endif /* CLIENT_H */
