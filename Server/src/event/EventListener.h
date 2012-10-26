/*
 * EventListener.h
 *
 *  Created on: Sep 24, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef EVENTLISTENER_H
#define EVENTLISTENER_H

#include "Common.h"

#include <list>

/**
 * Defines an listener for READ events used by the server.
 */
class EventListener {
  int maxNumOfEvents;

public:
  /**
   * @param maxNumOfEvents maximum number of events supported by the listener
   */
  EventListener(int maxNumOfEvents);

  ~EventListener();

  /**
   * Adds a READ event for a given socket.
   * @param socketDescriptor the client socket
   */
  bool addEvent(socket_descriptor_t socketDescriptor);

  /**
   * Deletes a event for a given socket.
   * @param socketDescriptor the client socket
   */
  bool deleteEvent(socket_descriptor_t socketDescriptor);

  /**
   * Retrieves the socket descriptors on which events were triggered.
   * This action blocks the thread until at least one event is triggered or an error occurs.
   * @return a list with all affected socket descriptors
   */
  std::list<socket_descriptor_t> getTriggeredEvents();
};

#endif /* EVENTLISTENER_H */
