/*
 * Process.h
 *
 * Created on: Sep 23, 2012
 * Author: cmihail  (Mihail Costea)
 *
 */

#ifndef PROCESS_H
#define PROCESS_H

#include <string>

class Process {
public:
  Process(std::string program, std::string programArgs[], int nrArgs);
};

#endif /* PROCESS_H */

