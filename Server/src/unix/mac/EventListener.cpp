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

EventListener::EventListener(int maxNumOfEvents) {
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

void EventListener::addEvent(int descriptor) {
  EV_SET(&changeList[currectNumOfEvents], descriptor,
      EVFILT_READ, EV_ADD | EV_ENABLE, 0, 0, 0);
  currectNumOfEvents++;
}

void EventListener::deleteEvent(int descriptor) {
  unsigned int i = 0;
  for (; i < currectNumOfEvents; i++) {
    if (changeList[i].ident == descriptor) {
      EV_SET(&changeList[i], descriptor, EVFILT_READ, EV_DELETE, 0, 0, 0);
      break;
    }
  }
  for (; i < currectNumOfEvents - 1; i++) {
    changeList[i] = changeList[i + 1]; // TODO(cmihail): not sure if it works, but it should
  }
  currectNumOfEvents--;
}

int EventListener::checkEvents() {
  numOfTriggeredEvents = kevent(kqueueFileDescriptor, changeList, currectNumOfEvents,
      eventList, currectNumOfEvents, NULL);
  return numOfTriggeredEvents;
}

int EventListener::getDescriptor(int eventId) {
  if (numOfTriggeredEvents <= 0  || eventId >= numOfTriggeredEvents ||
      eventList[eventId].filter != EVFILT_READ) {
    return -1;
  }
  return eventList[eventId].ident;
}
