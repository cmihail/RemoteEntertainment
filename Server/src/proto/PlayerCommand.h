/*
 * PlayerCommand.h
 *
 *  Created on: Sep 27, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef PLAYERCOMMAND_H
#define PLAYERCOMMAND_H

#include "Message.h"
#include "proto/player.pb.h"
#include "proto/PlayerMessage.h"

/**
 * Defines a wrapper for Command class contained in proto file.
 */
class PlayerCommand : public PlayerMessage {
public:
  /**
   * @param type the type of the command
   */
  PlayerCommand(proto::Command::Type type);

  /**
   * @param type the type of the command
   * @param info the information relevant to the command
   */
  PlayerCommand(proto::Command::Type type, std::string info);

  /**
   * @param coddedBuffer a coded message that contains a proto command
   */
  PlayerCommand(Message & codedBuffer);

  /**
   * @return the type of the command
   */
  proto::Command::Type getType();

  /**
   * @return true if command has information
   */
  bool hasInfo();

  /**
   * @return the information of the command
   */
  std::string getInfo();

protected:
  virtual google::protobuf::Message * toProto();

private:
  proto::Command_Type type;
  std::string info;
};

#endif /* PLAYERCOMMAND_H */
