/*
 * PlayerCommand.h
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail
 */

#ifndef PLAYERCOMMAND_H
#define PLAYERCOMMAND_H

#include "Message.h"
#include "proto/player.pb.h"

class PlayerCommand {
  proto::Command_Type type;
  std::string info;

public:
  PlayerCommand(proto::Command::Type type);
  PlayerCommand(proto::Command::Type type, std::string info);
  PlayerCommand(Message & codedBuffer);

  proto::Command toProto();
  proto::Command::Type getType();
  std::string getInformation();
  Message toCodedMessage();
};

#endif /* PLAYERCOMMAND_H */
