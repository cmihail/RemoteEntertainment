/**
 * TODO(cmihail): comments
 *
 * Author: cmihail(Mihail Costea)
 */

#include "Process.h"

#include <cassert>
#include <cstdlib>
#include <cstdio> // TODO(cmihail): only for dev

#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

using namespace std;

pid_t pid;

Process::Process(std::string program, int nrArgs, std::string programArgs[]) {
  // Pipe used for communication between the 2 processes.
  int pipefd[2];
  assert(pipe(pipefd) != -1); // TODO(cmihail): communication Server->MediaPlayer only for now

  // Create new process.
  pid = fork();
  char ** args = NULL;
  switch (pid) {
    case -1:
      printf("Couldn't fork\n");
      _exit(EXIT_FAILURE); // TODO(cmihail): LOG
      break;
    case 0:
      dup2(pipefd[0], STDIN_FILENO);

      // Convert program arguments in order to be used by execvp.
      args = (char **) malloc(sizeof(char *) * (nrArgs + 2));
      args[0] = strdup(program.c_str());
      for (int i = 0; i < nrArgs; i++) {
        args[i + 1] = strdup(programArgs[i].c_str());
      }
      args[nrArgs + 1] = NULL;

      execvp(args[0], args);
      printf("Couldn't exec movie player\n");
      _exit(EXIT_FAILURE); // TODO(cmihail): LOG
    default:
      dup2(pipefd[1], STDOUT_FILENO);
      break;
  }
}

void Process::read(file_descriptor &in) {

}

void Process::write(file_descriptor &out) {

}
