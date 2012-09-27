/*
 * ServerCommon.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVERCOMMON_H_
#define SERVERCOMMON_H_

class ServerCommon {
  unsigned int listenSocketFileDescriptor;

public:
  ServerCommon(int serverPort);
  ~ServerCommon();

  void run();
};


#endif /* SERVERCOMMON_H_ */
