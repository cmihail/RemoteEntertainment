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
#include "proto/PlayerMessage.h"

/**
 * Defines a wrapper for MessageHeader class contained in proto file.
 */
class PlayerMessageHeader : public PlayerMessage {
public:
  /**
   * @param the type of the message contained in the header
   */
  PlayerMessageHeader(proto::MessageHeader::Type messageType);

  /**
   * @param coddedBuffer a coded message that contains a proto message header
   */
  PlayerMessageHeader(Message & codedMessage);

  /**
   * @return the type of the message contained in the header
   */
  proto::MessageHeader::Type getMessageType();

protected:
  virtual google::protobuf::Message * toProto();

private:
  const proto::MessageHeader::Type messageType;
};

#endif /* PLAYERMESSAGEHEADER_H */
