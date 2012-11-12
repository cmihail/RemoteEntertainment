/*
 * PlayerMessage.cpp
 *
 *  Created on: Nov 5, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "proto/PlayerMessage.h"

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/io/zero_copy_stream_impl.h>

PlayerMessage::~PlayerMessage() {
  // Do nothing.
}

void PlayerMessage::parseFromCodedMessage(Message & codedMessage,
    google::protobuf::Message * protoMessageOut) {
  // Read the command from the buffer.
  google::protobuf::io::ZeroCopyInputStream * zeroCopyInputStream =
      new google::protobuf::io::ArrayInputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedInputStream * codedInputStream =
      new google::protobuf::io::CodedInputStream(zeroCopyInputStream);
  google::protobuf::uint32 size;
  codedInputStream->ReadVarint32(&size);
  codedInputStream->PushLimit(size);

  protoMessageOut->ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  // Free space.
  delete codedInputStream;
  delete zeroCopyInputStream;
}

Message PlayerMessage::toCodedMessage() {
  // Get player message as proto.
  google::protobuf::Message * protoMessage = this->toProto();

  // Create a message that will contain the coded command.
  int messageSize = protoMessage->ByteSize();
  Message codedMessage(messageSize + sizeof(int));

  // Serialize the command to the temporary coded buffer.
  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(messageSize);
  protoMessage->SerializeToCodedStream(codedOutputStream);

  // Free space.
  delete codedOutputStream;
  delete zeroCopyOutputStream;
  delete protoMessage;

  return codedMessage;
}
