/*
 * Logger.cpp
 *
 *  Created on: Oct 10, 2012
 *      Author: cmihail
 */

// TODO(cmihail): explain message format "[SERVER] ('Server.cpp', line 23, information): "

#include "Logger.h"

#include <cstdlib>
#include <iostream> // TODO(cmihail): maybe use a file instead

#define MAIN_HEADER "[SERVER]"

using namespace std;

void Logger::print(string file, int line, Type type, string message) {
  cout << MAIN_HEADER << " (\"" << file << "\", line " << line << ", " <<
      typeAsString(type) << "): " << message << endl;
  if (type == ERROR) {
    _exit(1);
  }
}

string Logger::typeAsString(Logger::Type type) {
  switch (type) {
    case INFORMATION:
      return "information";
    case WARNING:
      return "warning";
    case ERROR:
      return "error";
    default:
      return "undefined";
  }
}
