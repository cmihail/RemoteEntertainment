/*
 * PlayerMessageType.h
 *
 *  Created on: Oct 25, 2012
 *      Author: cmihail (Mihail Costea)
 * TODO(cmihail): comments
 */

#ifndef PLAYERMESSAGEHEADER_H
#define PLAYERMESSAGEHEADER_H

#include "Message.h"
#include "proto/player.pb.h"

// TODO(cmihail): common code with PlayerCommand -> methods related to coded message
class PlayerMessageHeader {
public:
  PlayerMessageHeader(proto::MessageHeader::Type messageType);
  PlayerMessageHeader(Message & codedMessage);

  proto::MessageHeader toProto();

  proto::MessageHeader::Type getMessageType();

  Message toCodedMessage();

private:
  proto::MessageHeader::Type messageType;
};

#endif /* PLAYERMESSAGEHEADER_H */
