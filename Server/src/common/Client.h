/*
 * Client.h
 *
 *  Created on: Sep 28, 2012
 *      Author: cmihail
 */

#ifndef CLIENT_H
#define CLIENT_H

#ifdef _WIN32
typedef HANDLE socket_descriptor_t;
#elif __linux__ || __APPLE__
typedef unsigned int socket_descriptor_t;
#else
  // TODO(cmihail): error
#endif

class Client {
  socket_descriptor_t socketDescriptor;

public:
  Client(socket_descriptor_t fileDescriptor);
  socket_descriptor_t getFileDescriptor();
};

#endif /* CLIENT_H */
