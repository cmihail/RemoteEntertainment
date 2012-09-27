/*
 * ServerUnix.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVERUNIXCOMMON_H_
#define SERVERUNIXCOMMON_H_

int serverUnixCommon_init(int serverPort, int maxNumOfClients);

int serverUnixCommon_newConnection(int listenSocketFileDescriptor);
void serverUnixCommon_endConnection();

#endif /* SERVERUNIXCOMMON_H_ */
