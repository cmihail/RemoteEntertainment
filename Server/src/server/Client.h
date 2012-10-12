/*
 * Client.h
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef CLIENT_H
#define CLIENT_H

#include "Common.h"

class Client {
  socket_descriptor_t socketDescriptor;

public:
  Client(socket_descriptor_t fileDescriptor);
  socket_descriptor_t getFileDescriptor();
};

#endif /* CLIENT_H */
