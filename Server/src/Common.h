/*
 * Common.h
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail
 */

#ifndef COMMON_H
#define COMMON_H

#ifdef _WIN32
  typedef HANDLE socket_descriptor_t;
#elif __linux__ || __APPLE__
  typedef int socket_descriptor_t;
#else
  // TODO(cmihail): error
#endif

#endif /* COMMON_H */
