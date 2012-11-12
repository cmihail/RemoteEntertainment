/*
 * PlayerDirectory.cpp
 *
 *  Created on: Nov 5, 2012
 *      Author: cmihail (Mihail Costea)
 */

#include "proto/PlayerDirectory.h"

using namespace std;

PlayerDirectory::PlayerDirectory(string path, string name) :
    path(path), name(name) {
  // Nothing to do.
}

void PlayerDirectory::addDirectory(PlayerDirectory directory) {
  directories.push_back(directory);
}

void PlayerDirectory::addFile(PlayerFile file) {
  files.push_back(file);
}

PlayerDirectory::PlayerFile::PlayerFile(const string name,
    const proto::Directory::File::Type type) : name(name), type(type) {
  // Nothing to do.
}
