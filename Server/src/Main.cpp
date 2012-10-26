/*
 * Main.cpp
 *
 *  Created on: Sep 23, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the main function which starts the server and the media player.
 */

#include "logger/Logger.h"
#include "process/Process.h"
#include "server/Server.h"

#include <cstdlib>
#include <iostream>
#include <string>

using namespace std;

/**
 * @param pathToMovie path to the movie that will be played
 * @param pathToLibVLC path to the libvlc as given in every Operating System
 * @param port the port on which the server is listening
 */
Process * createMediaPlayer(string pathToMovie, string pathToLibVLC, string port) {
  // TODO(cmihail): only for dev
  string program = "java";
  string * programArgs = new string[5];
  programArgs[0] = "-Djna.library.path=" + pathToLibVLC;
  programArgs[1] = "-jar";
  programArgs[2] = "media-player.jar";
  programArgs[3] = pathToMovie;
  programArgs[4] = port;

  return new Process(program, programArgs, 5);
}

int main(int argc, char ** argv) {
  // TODO(cmihail): only for dev, only port should be as param
  if (argc != 4) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE,
        "./Main <path_to_movie> <path_to_libvlc> <port>");
  }

  Server * server = new Server(atoi(argv[3]));
  createMediaPlayer(argv[1], argv[2], argv[3]);
  server->run();
  delete server;
}
