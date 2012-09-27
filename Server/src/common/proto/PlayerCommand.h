/*
 * PlayerCommand.h
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail
 */

#ifndef PLAYERCOMMAND_H
#define PLAYERCOMMAND_H

class PlayerCommand {
  proto::Command_Type type;
  std::string info;

public:
  PlayerCommand(proto::Command::Type type);
  PlayerCommand(proto::Command::Type type, std::string info);
  PlayerCommand(std::string codedBuffer);
  ~PlayerCommand();

  proto::Command toProto();
  proto::Command::Type getType();
  std::string getInformation();
  std::string toCodedBuffer();
};

#endif /* PLAYERCOMMAND_H */
