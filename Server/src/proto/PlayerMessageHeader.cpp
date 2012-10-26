/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of PlayerMessageHeader.h.
 */

#include <iostream> // TODO

#include "proto/PlayerMessageHeader.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

using namespace std;

int headerMessageSize = -1;

PlayerMessageHeader::PlayerMessageHeader(proto::MessageHeader::Type messageType)
    : messageType(messageType) {
  // Nothing to do
}

PlayerMessageHeader::PlayerMessageHeader(Message & codedMessage) {
  // Read the command from the temporary buffer.
  google::protobuf::io::ZeroCopyInputStream * zeroCopyInputStream =
      new google::protobuf::io::ArrayInputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedInputStream * codedInputStream =
      new google::protobuf::io::CodedInputStream(zeroCopyInputStream);
  google::protobuf::uint32 size;
  codedInputStream->ReadVarint32(&size);
  codedInputStream->PushLimit(size);

  proto::MessageHeader header;
  header.ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  // Set message type and length.
  messageType = header.messagetype();

  // Free space.
  delete codedInputStream;
  delete zeroCopyInputStream;
}

proto::MessageHeader PlayerMessageHeader::toProto() {
  proto::MessageHeader header;
  header.set_messagetype(messageType);
  return header;
}

proto::MessageHeader::Type PlayerMessageHeader::getMessageType() {
  return messageType;
}

Message PlayerMessageHeader::toCodedMessage() {
  proto::MessageHeader header = this->toProto();
  header.GetCachedSize();
  // Create a message that will contain the coded command.
  Message codedMessage(header.ByteSize() + sizeof(int) + 1);

  // Serialize the command to the temporary coded buffer.
  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(header.ByteSize());
  if (!header.SerializeToCodedStream(codedOutputStream)) {
    // TODO(cmihail): logger
  }

  // Free space.
  delete codedOutputStream;
  delete zeroCopyOutputStream;

  return codedMessage;
}
