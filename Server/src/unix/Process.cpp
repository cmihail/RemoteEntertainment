/*
 * Process.cpp
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail
 */

#include "../common/Process.h"

#include <cassert>
#include <cstdlib>
#include <cstdio> // TODO(cmihail): only for dev

#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

using namespace std;

pid_t pid;

Process::Process(std::string program, int nrArgs, std::string programArgs[]) {
  // Create new process.
  pid = fork();
  char ** args = NULL;
  assert(pid >= 0);

  if (pid == 0) {
    // Convert program arguments in order to be used by execvp.
    args = (char **) malloc(sizeof(char *) * (nrArgs + 2));
    args[0] = strdup(program.c_str());
    for (int i = 0; i < nrArgs; i++) {
      args[i + 1] = strdup(programArgs[i].c_str());
    }
    args[nrArgs + 1] = NULL;

    execvp(args[0], args);
    printf("[Server] Couldn't exec media player\n");
    _exit(EXIT_FAILURE); // TODO(cmihail): LOG
  }
}
