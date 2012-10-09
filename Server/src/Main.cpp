/*
 * Main.cpp
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail
 */

#include "platform/Process.h"
#include "server/Server.h"

#include <cassert>

#include <iostream>
#include <string>

#include <fcntl.h>
#include <sys/types.h>
#include <sys/event.h>
#include <sys/time.h>

using namespace std;

Process * createMediaPlayer(string pathToMovie, string port) {
  // TODO(cmihail): only for dev
  string program = "java";
  string * programArgs = new string[5];
  programArgs[0] = "-Djna.library.path=/Applications/VLC.app/Contents/MacOS/lib";
  programArgs[1] = "-jar";
  programArgs[2] = "media-player.jar";
  programArgs[3] = pathToMovie;
  programArgs[4] = port;

  return new Process(program, 5, programArgs);
}

int main(int argc, char ** argv) {
  if (argc != 3) {
    cerr << "Usage: ./MainServer <path_to_movie> <port>\n";
    return 1;
  }

  Server * server = new Server(atoi(argv[2]));
  createMediaPlayer(argv[1], argv[2]);
  server->run();
  delete server;
}
