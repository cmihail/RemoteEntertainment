/*
 * Process.cpp
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "Logger.h"
#include "platform/Process.h"

#include <cstdlib>
#include <cstring>

#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

Process::Process(string program, string programArgs[], int nrArgs) {
  // Create new process.
  pid_t pid = fork();
  char ** args = NULL;
  if (pid == -1) {
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Couldn't fork");
  }

  if (pid == 0) {
    // Convert program arguments in order to be used by execvp.
    args = (char **) malloc(sizeof(char *) * (nrArgs + 2));
    args[0] = strdup(program.c_str());
    for (int i = 0; i < nrArgs; i++) {
      args[i + 1] = strdup(programArgs[i].c_str());
    }
    args[nrArgs + 1] = NULL;

    execvp(args[0], args);
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Couldn't exec media player");
  }
}
