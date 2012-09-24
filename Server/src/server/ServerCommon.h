/*
 * Server.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVER_H_
#define SERVER_H_

class ServerCommon {
  int listenSocketFileDescriptor;

public:
  ServerCommon(int serverPort);
  ~ServerCommon();

  void run();
};


#endif /* SERVER_H_ */
