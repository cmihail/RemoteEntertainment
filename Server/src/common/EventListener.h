/*
 * EventListener.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef EVENTLISTENER_H
#define EVENTLISTENER_H

#include "Common.h"

// TODO(cmihail): mention that it listens only READ events
class EventListener {
  int maxNumOfEvents;

public:
  EventListener(int maxNumOfEvents);
  ~EventListener();

  bool addEvent(socket_descriptor_t descriptor);
  bool deleteEvent(socket_descriptor_t descriptor);

  int checkEvents(); // Should be executed before getDescriptor | Can be united with getDescriptor
  socket_descriptor_t getDescriptor(int eventId);
};

#endif /* EVENTLISTENER_H */
