/*
 * Common.h
 *
 *  Created on: Oct 8, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines common definitions for all platforms.
 */

#ifndef COMMON_H
#define COMMON_H

#ifdef _WIN32
  #include <windows.h>
  typedef SOCKET socket_descriptor_t;
#elif defined(__APPLE__) || defined(__linux__)
  typedef int socket_descriptor_t;
#else
  // TODO(cmihail): error
#endif

#endif /* COMMON_H */
