/*
 * EventListener.cpp
 *
 *  Created on: Oct 13, 2012
 *      Author: Mihail Costea
 */

#include "Logger.h"
#include "platform/EventListener.h"

#include <windows.h>

HANDLE ioCompletionPort;
SOCKET inputSocket; // TODO(cmihail): another name

EventListener::EventListener(int maxNumOfEvents) : maxNumOfEvents(maxNumOfEvents) {
  // Create event notifier.
  ioCompletionPort = CreateIoCompletionPort(INVALID_HANDLE_VALUE, NULL, (ULONG_PTR) NULL, 0);
  if (ioCompletionPort == NULL) {
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "IOCompletionPort error");
  }
}

EventListener::~EventListener() {

}

bool EventListener::addEvent(socket_descriptor_t descriptor) {
  if (descriptor != INVALID_SOCKET) { // TODO function
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Invalid descriptor");
    return false;
  }

  // Check if maximum number of events was reached.
  if (currentNumOfEvents == maxNumOfEvents) {
    return false;
  }

  HANDLE handle = CreateIoCompletionPort((HANDLE) descriptor, ioCompletionPort,
      (ULONG_PTR) descriptor, INFINITE);
  if (handle == NULL) {
    Logger::print(__FILE__, __LINE__, Logger::WARNING, "Couldn't create completion port");
    return false;
  }
  return true;
}

bool EventListener::deleteEvent(socket_descriptor_t descriptor) {
  if (descriptor != INVALID_SOCKET) { // TODO function
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Invalid descriptor");
    return false;
  }

  // TODO in the future
  return true;
}

int EventListener::checkEvents() {
  DWORD bytes;
  ULONG_PTR key;
  LPOVERLAPPED overlapped;

  if (!GetQueuedCompletionStatus(ioCompletionPort, &bytes, &key, &overlapped, INFINITE)) {
    return -1;
  }
  inputSocket = (SOCKET) key;
  return 1;
}

socket_descriptor_t EventListener::getDescriptor(int eventId) {
  return inputSocket;
}






