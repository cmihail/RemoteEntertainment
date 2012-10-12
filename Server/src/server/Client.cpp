/*
 * Client.cpp
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "server/Client.h"

Client::Client(socket_descriptor_t socketDescriptor) : socketDescriptor(socketDescriptor) {
  // Nothing to do
}

socket_descriptor_t Client::getFileDescriptor() {
  return socketDescriptor;
}
