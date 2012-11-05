/*
 * EventListener.cpp
 *
 *  Created on: Oct 13, 2012
 *      Author: Mihail Costea
 *
 * Defines the implementation of EventListener.h.
 */

#include "server/event/EventListener.h"
#include "logger/Logger.h"

#include <string>

using namespace std;

HANDLE ioCompletionPort;
int currentNumOfEvents = 0;

EventListener::EventListener(int maxNumOfEvents) : maxNumOfEvents(maxNumOfEvents) {
  // Create event notifier.
  ioCompletionPort = CreateIoCompletionPort(INVALID_HANDLE_VALUE, NULL, (ULONG_PTR) NULL, 0);
  if (ioCompletionPort == NULL) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, "IOCompletionPort error");
  }
}

EventListener::~EventListener() {

}

static bool checkSocket(socket_descriptor_t descriptor, string file, int line) {
  if (descriptor == INVALID_SOCKET) {
    Logger::print(file, line, Logger::SEVERE, "Invalid descriptor");
    return false;
  }
  return true;
}

bool EventListener::addEvent(socket_descriptor_t descriptor) {
  if (!checkSocket(descriptor, __FILE__, __LINE__)) {
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

  currentNumOfEvents++;
  return true;
}

bool EventListener::deleteEvent(socket_descriptor_t descriptor) {
  if (!checkSocket(descriptor, __FILE__, __LINE__)) {
    return false;
  }

  // TODO(cmihail): event removal in the future
  return true;
}

// TODO(cmihail) unite this with getDescriptor
list<socket_descriptor_t> EventListener::getTriggeredEvents() {
  DWORD bytes;
  ULONG_PTR key;
  LPOVERLAPPED overlapped;

  list<socket_descriptor_t> triggeredEvents;
  if (GetQueuedCompletionStatus(ioCompletionPort, &bytes, &key, &overlapped, INFINITE)) {
    triggeredEvents.push_back((socket_descriptor_t) key);
  }
  return triggeredEvents;
}
