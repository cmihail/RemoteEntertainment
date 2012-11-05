/*
 * PlayerDirectory.h
 *
 *  Created on: Nov 5, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef PLAYERDIRECTORY_H
#define PLAYERDIRECTORY_H

#include "Message.h"
#include "proto/player.pb.h"
#include "proto/PlayerMessage.h"

#include <list>

/**
 * Defines a wrapper for Directory class contained in proto file.
 */
class PlayerDirectory : public PlayerMessage {
public:
  /**
   * TODO(cmihail): comments
   */
  class PlayerFile {
  public:
    PlayerFile(const std::string name, const proto::Directory::File::Type type);

  private:
    const std::string name;
    const proto::Directory::File::Type type;
  };

  PlayerDirectory(const std::string path, const std::string name);

  void addDirectory(PlayerDirectory directory);
  void addFile(PlayerFile file);

protected:
  virtual google::protobuf::Message * toProto();

private:
  const std::string path;
  const std::string name;
  std::list<PlayerDirectory> directories;
  std::list<PlayerFile> files;
};

#endif /* PLAYERDIRECTORY_H */
