/*
 * Server.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVER_H_
#define SERVER_H_

class Server {
public:
  Server(int serverPort);
  virtual ~Server();

  void run();
};


#endif /* SERVER_H_ */
