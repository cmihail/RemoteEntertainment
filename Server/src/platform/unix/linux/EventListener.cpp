/*
 * EventListener.cpp
 *
 *  Created on: Oct 12, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "Logger.h"
#include "platform/EventListener.h"

#include <cstdlib>

#include <sys/epoll.h>

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
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "epoll error");
  }
  changeList = new struct epoll_event[maxNumOfEvents];
  eventList = new struct epoll_event[maxNumOfEvents];
}

EventListener::~EventListener() {
  delete changeList;
}

bool EventListener::addEvent(socket_descriptor_t descriptor) {
  if (descriptor < 0) {
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Invalid descriptor");
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
  if (descriptor < 0) {
    Logger::print(__FILE__, __LINE__, Logger::ERROR, "Invalid descriptor");
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

int EventListener::checkEvents() {
  numOfTriggeredEvents = epoll_wait(epollDescriptor, eventList, currentNumOfEvents, -1);
  return numOfTriggeredEvents;
}

socket_descriptor_t EventListener::getDescriptor(int eventId) {
  if (numOfTriggeredEvents <= 0  || eventId >= numOfTriggeredEvents ||
      eventList[eventId].events != EPOLLIN) {
    return -1;
  }
  return eventList[eventId].data.fd;
}
