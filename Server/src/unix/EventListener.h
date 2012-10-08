/*
 * EventListener.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail
 */

#ifndef EVENTLISTENER_H
#define EVENTLISTENER_H

// TODO(cmihail): mention that it listens only READ events
class EventListener {
public:
  EventListener(int maxNumOfEvents);
  ~EventListener();

  void addEvent(int descriptor);
  void deleteEvent(int descriptor);

  int checkEvents(); // Should be executed before getDescriptor
  int getDescriptor(int eventId);
};

#endif /* EVENTLISTENER_H */
