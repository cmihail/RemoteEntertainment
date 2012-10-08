/*
 * ServerUnix.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVERUNIXCOMMON_H_
#define SERVERUNIXCOMMON_H_

int serverUnixCommon_init(int serverPort, int maxNumOfClients);

int serverUnixCommon_newConnection(int listenSocket);
void serverUnixCommon_endConnection(int socket);

#endif /* SERVERUNIXCOMMON_H_ */
