/*
 * Server.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVER_H_
#define SERVER_H_

class Server {
  unsigned int listenSocket; // TODO(cmihail): change to file_descriptor_t

public:
  Server(int serverPort);
  ~Server();

  void run();
};


#endif /* SERVER_H_ */
