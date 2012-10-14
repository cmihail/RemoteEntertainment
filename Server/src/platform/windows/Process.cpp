/*
 * Process.cpp
 *
 *  Created on: Oct 13, 2012
 *      Author: Mihail Costea
 */

#include "Logger.h"
#include "platform/Process.h"

#include <cstdlib>
#include <sstream>

#include <windows.h>

using namespace std;

Process::Process(string program, string programArgs[], int nrArgs) {
  STARTUPINFO si;
  PROCESS_INFORMATION pi;
  int size = sizeof(si);
  ZeroMemory(&si, size);
  si.cb = size;
  ZeroMemory(&pi, sizeof(pi));

  stringstream streamBuffer;
  streamBuffer << "\"" << program << "\"";
  for (int i = 0; i < nrArgs; i++) {
    streamBuffer << " \"" << programArgs[i] << "\""; // TODO(cmihail): resolve problem with " ... "
  }

  char * cmdLine = strdup(streamBuffer.str().c_str());
  Logger::print(__FILE__, __LINE__, Logger::INFORMATION, cmdLine);
  if (CreateProcess(NULL, cmdLine, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi) == FALSE) {
    Logger::print(string(__FILE__), __LINE__, Logger::SEVERE, "Couldn't create process");
  }
  delete cmdLine;
}
