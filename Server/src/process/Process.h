/*
 * Process.h
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef PROCESS_H
#define PROCESS_H

#include <string>

/**
 * Defines actions and information for a new process.
 */
class Process {
public:
  /**
   * Starts a new process.
   * @param program the program that must be executed
   * @param programArgs the arguments of the program
   * @param nrArgs the number of arguments
   */
  Process(std::string program, std::string programArgs[], int nrArgs);
};

#endif /* PROCESS_H */

