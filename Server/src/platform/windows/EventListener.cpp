/*
 * EventListener.cpp
 *
 *  Created on: Oct 13, 2012
 *      Author: Mihail Costea
 *
 * Defines the implementation of EventListener.h.
 */

#include "Logger.h"
#include "platform/EventListener.h"

#include <string>

using namespace std;

HANDLE ioCompletionPort;
SOCKET inputSocket; // TODO(cmihail): another name
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

  // TODO(cmihail) removal in the future
  return true;
}

// TODO(cmihail) unite this with getDescriptor
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
