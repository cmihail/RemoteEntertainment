/*
 * Main.cpp
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail
 */

#include "Logger.h"
#include "platform/Process.h"
#include "server/Server.h"

#include <iostream>
#include <string>

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
  // TODO(cmihail): only for dev, should be only port as param
  if (argc != 3) {
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "./Main <path_to_movie> <port>");
  }

  Server * server = new Server(atoi(argv[2]));
  createMediaPlayer(argv[1], argv[2]);
  server->run();
  delete server;
}
