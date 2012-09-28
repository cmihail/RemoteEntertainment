/*
 * Client.cpp
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail
 */

#include "Client.h"

Client::Client(socket_descriptor_t socketDescriptor) : socketDescriptor(socketDescriptor) {
  // Nothing to do
}

socket_descriptor_t Client::getFileDescriptor() {
  return socketDescriptor;
}
