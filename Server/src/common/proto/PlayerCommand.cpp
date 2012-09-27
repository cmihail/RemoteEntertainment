/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail
 */

#include "PlayerCommand.h"
#include "player.pb.h"

using namespace std;

PlayerCommand::PlayerCommand(proto::Command::Type type) : type(type), info(NULL) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(proto::Command::Type type, string info) : type(type), info(info) {
  // Nothing to do
}

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
  google::protobuf::io::CodedInputStream::Limit commandLimit =
      codedInputStream->PushLimit(size);

  proto::Command command = new proto::Command();
  command.ParseFromCodedStream(codedInputStream);
  codedInputStream->PopLimit(size);

  // Set type and info and free space.
  type = command.type();
  if (command.has_info()) {
    info = command.info();
  } else {
    info = NULL;
  }

  delete codedInputStream;
  delete zeroCopyInputStream;
  delete buffer;
}

proto::Command PlayerCommand::toProto() {
  proto::Command command;
  command.set_type(type);
  if (info != NULL) {
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

string PlayerCommand::toCodedBuffer() {
  proto::Command command = this->toProto();

  // Create a temporary buffer to contain the coded command.
  int bufferSize = command.ByteSize() + sizeof(bufferSize);
  char * buffer = new char[bufferSize];

  // Serialize the command to the temporary coded buffer.
  google::protobuf::io::ZeroCopyOutputStream * zeroCopyOutputStream =
      new google::protobuf::io::ArrayOutputStream(buffer, bufferSize);
  google::protobuf::io::CodedOutputStream * codedOutputStream =
      new google::protobuf::io::CodedOutputStream(zeroCopyOutputStream);
  codedOutputStream->WriteVarint32(command.ByteSize());
  assert(command.SerializeToCodedStream(codedOutputStream));

  // Create coded buffer and free space.
  string codedBuffer(bufferSize);

  delete codedOutputStream;
  delete zeroCopyOutputStream;
  delete buffer;

  return codedBuffer;
}
