/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of PlayerCommand.h.
 */

#include "proto/PlayerCommand.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#define EMPTY_STRING ""

using namespace std;

PlayerCommand::PlayerCommand(proto::Command::Type type) : type(type), info(EMPTY_STRING) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(proto::Command::Type type, string info) : type(type), info(info) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(Message & codedMessage) {
  // Read the command from the temporary buffer.
  google::protobuf::io::ZeroCopyInputStream * zeroCopyInputStream =
      new google::protobuf::io::ArrayInputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedInputStream * codedInputStream =
      new google::protobuf::io::CodedInputStream(zeroCopyInputStream);
  google::protobuf::uint32 size;
  codedInputStream->ReadVarint32(&size);
  codedInputStream->PushLimit(size);

  proto::Command command;
  command.ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  // Set type and info.
  type = command.type();
  if (command.has_info()) {
    info = command.info().value();
  } else {
    info = EMPTY_STRING;
  }

  // Free space.
  delete codedInputStream;
  delete zeroCopyInputStream;
}

proto::Command PlayerCommand::toProto() {
  proto::Command command;
  command.set_type(type);
  if (info.compare(EMPTY_STRING)) {
    proto::Command::Information commandInfo = command.info();
    commandInfo.set_value(info);
  }
  return command;
}

proto::Command::Type PlayerCommand::getType() {
  return type;
}

bool PlayerCommand::hasInfo() {
  if (info.compare(EMPTY_STRING) == 0) {
    return true;
  }
  return false;
}

string PlayerCommand::getInfo() {
  return info;
}

Message PlayerCommand::toCodedMessage() {
  proto::Command command = this->toProto();

  // Create a message that will contain the coded command.
  Message codedMessage(command.ByteSize() + sizeof(int) + 1);

  // Serialize the command to the temporary coded buffer.
  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(codedMessage.getContent(),
          codedMessage.getLength());
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(command.ByteSize());
  assert(command.SerializeToCodedStream(codedOutputStream)); // TODO(cmihail): logger

  // Free space.
  delete codedOutputStream;
  delete zeroCopyOutputStream;

  return codedMessage;
}
