/*
 * ServerUnixCommon.cpp
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#include "../EventListener.h"

#include <cassert>
#include <cstdlib>

#include <sys/event.h>

int kqueueFileDescriptor;
struct kevent * changeList;
struct kevent * eventList;

int numOfTriggeredEvents = -1;
int currectNumOfEvents = 0;

EventListener::EventListener(int maxNumOfEvents) : maxNumOfEvents(maxNumOfEvents) {
  // Create event notifier.
  kqueueFileDescriptor = kqueue();
  assert(kqueueFileDescriptor != -1);
  changeList = new struct kevent[maxNumOfEvents]; // TODO(cmihail): maybe realloc when needed
  eventList = new struct kevent[maxNumOfEvents];
}

EventListener::~EventListener() {
  delete changeList;
  delete eventList;
}

bool EventListener::addEvent(socket_descriptor_t descriptor) {
  // Check if maximum number of events was reached.
  if (currectNumOfEvents == maxNumOfEvents) {
    return false;
  }

  EV_SET(&changeList[currectNumOfEvents], descriptor,
      EVFILT_READ, EV_ADD | EV_ENABLE, 0, 0, 0);
  currectNumOfEvents++;
  return true;
}

bool EventListener::deleteEvent(socket_descriptor_t descriptor) {
  // Search for the correspondent event for the given descriptor.
  int i = 0;
  for (; i < currectNumOfEvents; i++) {
    if (changeList[i].ident == descriptor) {
      // Delete event from kqueue.
      EV_SET(&changeList[i], descriptor, EVFILT_READ, EV_DELETE, 0, 0, 0);
      struct kevent tempEventList;
      kevent(kqueueFileDescriptor, &changeList[i], 1, &tempEventList, 1, NULL);
      break;
    }
  }

  // Check if the event was found.
  if (i == currectNumOfEvents) {
    return false;
  }

  // Move all remaining events to the left.
  for (; i < currectNumOfEvents - 1; i++) {
    changeList[i] = changeList[i + 1]; // TODO(cmihail): not sure if it works, but it should
  }
  currectNumOfEvents--;

  return true;
}

int EventListener::checkEvents() {
  numOfTriggeredEvents = kevent(kqueueFileDescriptor, changeList, currectNumOfEvents,
      eventList, currectNumOfEvents, NULL);
  return numOfTriggeredEvents;
}

socket_descriptor_t EventListener::getDescriptor(int eventId) {
  if (numOfTriggeredEvents <= 0  || eventId >= numOfTriggeredEvents ||
      eventList[eventId].filter != EVFILT_READ) {
    return -1;
  }
  return eventList[eventId].ident;
}
