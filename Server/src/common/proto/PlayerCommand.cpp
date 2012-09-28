/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail
 */

#include "PlayerCommand.h"

#include <google/protobuf/io/zero_copy_stream_impl.h>

#define EMPTY_STRING ""

using namespace std;

PlayerCommand::PlayerCommand(proto::Command::Type type) : type(type), info(EMPTY_STRING) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(proto::Command::Type type, string info) : type(type), info(info) {
  // Nothing to do
}

// TODO(cmihail): see if string(dataBuffer) can produce problems
PlayerCommand::PlayerCommand(string commandBuffer) {
  // Create a temporary buffer to contain the coded command.
  int bufferSize = commandBuffer.length();
  char * buffer = new char[bufferSize];
  memcpy(buffer, commandBuffer.c_str(), bufferSize);

  // Read the command from the temporary buffer.
  google::protobuf::io::ZeroCopyInputStream * zeroCopyInputStream =
      new google::protobuf::io::ArrayInputStream(buffer, bufferSize);
  google::protobuf::io::CodedInputStream * codedInputStream =
      new google::protobuf::io::CodedInputStream(zeroCopyInputStream);
  google::protobuf::uint32 size;
  codedInputStream->ReadVarint32(&size);
//  google::protobuf::io::CodedInputStream::Limit commandLimit = TODO(cmihail): see if needed
  codedInputStream->PushLimit(size);

  proto::Command command;
  command.ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  // Set type and info and free space.
  type = command.type();
  if (command.has_info()) {
    info = command.info().value();
  } else {
    info = EMPTY_STRING;
  }

  delete codedInputStream;
  delete zeroCopyInputStream;
  delete buffer;
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

string PlayerCommand::getInformation() {
  return info;
}

// TODO(cmihail): see if string(dataBuffer) can produce problems
string PlayerCommand::toCodedBuffer() {
  proto::Command command = this->toProto();

  // Create a temporary buffer to contain the coded command.
  int bufferSize = command.ByteSize() + sizeof(bufferSize) + 1;
  char * buffer = new char[bufferSize];
  memset(buffer, 0, bufferSize);

  // Serialize the command to the temporary coded buffer.
  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(buffer, bufferSize);
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(command.ByteSize());
  assert(command.SerializeToCodedStream(codedOutputStream));

  // Create coded buffer and free space.
  string codedBuffer(buffer);

  delete codedOutputStream;
  delete zeroCopyOutputStream;
  delete buffer;

  return codedBuffer;
}
