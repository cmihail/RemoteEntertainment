/*
 * PlayerCommand.cpp
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "proto/PlayerCommand.h"

#define EMPTY_STRING ""

using namespace std;

PlayerCommand::PlayerCommand(proto::Command::Type type) : type(type), info(EMPTY_STRING) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(proto::Command::Type type, string info) : type(type), info(info) {
  // Nothing to do
}

PlayerCommand::PlayerCommand(Message & codedMessage) {
  proto::Command * command = new proto::Command();
  parseFromCodedMessage(codedMessage, command);

  // Set type and info.
  type = command->type();
  if (command->has_info()) {
    info = command->info().value();
  } else {
    info = EMPTY_STRING;
  }

  delete command;
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

google::protobuf::Message * PlayerCommand::toProto() {
  proto::Command * command = new proto::Command();
  command->set_type(type);
  if (info.compare(EMPTY_STRING)) {
    proto::Command::Information commandInfo = command->info();
    commandInfo.set_value(info);
  }
  return command;
}
