/*
 * EventListener.cpp
 *
 *  Created on: Oct 12, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of EventListener.h.
 */

#include "server/event/EventListener.h"
#include "logger/Logger.h"

#include <cstdlib>
#include <string>

#include <sys/epoll.h>

using namespace std;

int epollDescriptor;

struct epoll_event * changeList;
struct epoll_event * eventList;

int currentNumOfEvents = 0;
int numOfTriggeredEvents = -1;

// TODO(cmihail): maybe a method for closing

EventListener::EventListener(int maxNumOfEvents) : maxNumOfEvents(maxNumOfEvents) {
  // Create event notifier.
  epollDescriptor = epoll_create(maxNumOfEvents);
  if (epollDescriptor == -1) {
    Logger::print(__FILE__, __LINE__, Logger::SEVERE, "epoll error");
  }
  changeList = new struct epoll_event[maxNumOfEvents];
  eventList = new struct epoll_event[maxNumOfEvents];
}

EventListener::~EventListener() {
  delete changeList;
}

static bool checkSocket(socket_descriptor_t descriptor, string file, int line) {
  if (descriptor == -1) {
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

  changeList[currentNumOfEvents].data.fd = descriptor;
  changeList[currentNumOfEvents].events = EPOLLIN;
  if (epoll_ctl(epollDescriptor, EPOLL_CTL_ADD, descriptor,
      &changeList[currentNumOfEvents]) == -1) {
    Logger::print(__FILE__, __LINE__, Logger::WARNING, "Could not add event in epoll");
    return false;
  }
  currentNumOfEvents++;
  return true;
}

bool EventListener::deleteEvent(socket_descriptor_t descriptor) {
  if (!checkSocket(descriptor, __FILE__, __LINE__)) {
    return false;
  }

  // Search for the correspondent event for the given descriptor.
  int i = 0;
  for (; i < currentNumOfEvents; i++) {
    if (changeList[i].data.fd == (unsigned int) descriptor) {
      // Delete event from epoll. // TODO(cmihail): test if executed
      if (epoll_ctl(epollDescriptor, EPOLL_CTL_DEL, descriptor,
          &changeList[currentNumOfEvents]) == -1) {
        Logger::print(__FILE__, __LINE__, Logger::WARNING, "Could not delete event from epoll");
            return false;
      }
      break;
    }
  }

  // Check if the event was found.
  if (i == currentNumOfEvents) {
    return false;
  }

  // Move all remaining events to the left.
  for (; i < currentNumOfEvents - 1; i++) {
    changeList[i] = changeList[i + 1];
  }
  currentNumOfEvents--;

  return true;
}

list<socket_descriptor_t> EventListener::getTriggeredEvents() {
  int numOfTriggeredEvents = epoll_wait(epollDescriptor, eventList, currentNumOfEvents, -1);
  list<socket_descriptor_t> triggeredEvents;
  for (int i = 0; i < numOfTriggeredEvents; i++) {
    if (eventList[i].events == EPOLLIN) {
      triggeredEvents.push_back(eventList[i].data.fd);
    }
  }
  return triggeredEvents;
}
