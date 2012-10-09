/*
 * ServerUnix.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVERUNIXCOMMON_H_
#define SERVERUNIXCOMMON_H_

#include "../common/Message.h"

int serverUnixCommon_init(int serverPort, int maxNumOfClients);

int serverUnixCommon_newConnection(int listenSocket);
void serverUnixCommon_endConnection(int socket);

Message serverUnixCommon_receive(int socket);
void serverUnixCommon_send(int socket, Message & message);

#endif /* SERVERUNIXCOMMON_H_ */
