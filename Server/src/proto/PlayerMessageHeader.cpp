/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of PlayerMessageHeader.h.
 */

#include "proto/PlayerMessageHeader.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

using namespace std;

int headerMessageSize = -1;

PlayerMessageHeader::PlayerMessageHeader(proto::MessageHeader::Type messageType)
    : messageType(messageType) {
  // Nothing to do
}

PlayerMessageHeader::PlayerMessageHeader(Message & codedMessage) {
  proto::MessageHeader * header = new proto::MessageHeader();
  parseFromCodedMessage(codedMessage, header);

  // Set message type and length.
  messageType = header->type();

  delete header;
}

proto::MessageHeader::Type PlayerMessageHeader::getMessageType() {
  return messageType;
}

google::protobuf::Message * PlayerMessageHeader::toProto()  {
  proto::MessageHeader * header = new proto::MessageHeader();
  header->set_type(messageType);
  return header;
}
