/*
 * Logger.cpp
 *
 *  Created on: Oct 10, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of Logger.h.
 * Message format: [SERVER] (<file>, line <line_num>, <warning level>): <message>
 */

#include "Logger.h"

#include <cstdlib>
#include <iostream>

#define MAIN_HEADER "[SERVER]"

using namespace std;

void Logger::print(string file, int line, Type type, string message) {
  cout << MAIN_HEADER << " (" << file << ", line " << line << ", " <<
      typeAsString(type) << "): " << message << endl;
  if (type == SEVERE) {
    _exit(1);
  }
}

string Logger::typeAsString(Logger::Type type) {
  switch (type) {
    case INFO:
      return "info";
    case WARNING:
      return "warning";
    case SEVERE:
      return "severe";
    default:
      return "undefined";
  }
}
