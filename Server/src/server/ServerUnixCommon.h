/*
 * ServerUnix.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef SERVERUNIX_H_
#define SERVERUNIX_H_

int serverUnixCommon_init(int serverPort, int maxNumOfClients);

int serverUnixCommon_newConnection(int listenSocketFileDescriptor);
void serverUnixCommon_endConnection();

#endif /* SERVERUNIX_H_ */
