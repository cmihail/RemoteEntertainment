/**
 * TODO(cmihail): comments
 *
 * Author: cmihail(Mihail Costea)
 */

#ifndef PROCESS_H
#define PROCESS_H

#include <string>

typedef struct file_descriptor {
#if defined __linux__ || TARGET_OS_MAC
  int fd;
#elif defined _WIN32
  HANDLE handle;
#else
//  #error "Unknown platform" // TODO(cmihail): use something else
#endif
} file_descriptor_t;

class Process {
public:
  Process(std::string program, int nrArgs, std::string programArgs[]);
};

#endif
