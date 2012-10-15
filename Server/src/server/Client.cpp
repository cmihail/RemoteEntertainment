/*
 * Client.cpp
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of Client.h.
 */

#include "server/Client.h"

Client::Client(socket_descriptor_t socketDescriptor) : socketDescriptor(socketDescriptor) {
  // Nothing to do
}

socket_descriptor_t Client::getSocketDescriptor() {
  return socketDescriptor;
}
