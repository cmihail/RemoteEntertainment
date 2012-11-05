/*
 * PlayerMessage.h
 *
 *  Created on: Nov 5, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef PLAYERMESSAGE_H
#define PLAYERMESSAGE_H

#include "Message.h"
#include "player.pb.h"

/**
 * Defines common methods for all player messages defined in player.pb.h.
 *
 * A good practice would be to define a constructor with parameter <Message & codedMessage>
 * in order to create an object from a coded message (use <parseFromCodedMessage> method
 * to get the proto message from the coded message), but only if necessary.
 */
class PlayerMessage {
public:
  virtual ~PlayerMessage();

  /**
   * @return player message as a coded message
   */
  virtual Message toCodedMessage();

protected:
  /**
   * Parses a proto message from a coded message. The parsed message is going to be saved into
   * one of the method's parameters because google::protobuf::Message is abstract
   * and it should be initialized before the call.
   * @param codedMessage the coded message that is parsed
   * @param protoMessageOut where the message is saved
   */
  void parseFromCodedMessage(Message & codedMessage, google::protobuf::Message * protoMessageOut);

  /**
   * @return player message as proto message
   */
  virtual google::protobuf::Message * toProto() = 0;
};

#endif /* PLAYERMESSAGE_H */
