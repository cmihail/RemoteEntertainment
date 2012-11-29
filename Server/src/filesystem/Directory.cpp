/*
 * Directory.cpp
 *
 *  Created on: Nov 29, 2012
 *      Author: cmihail
 */

#include "filesystem/Directory.h"
#include "logger/Logger.h"

#include <algorithm>
#include <sstream>
#include <vector>

#include <dirent.h>
#include <sys/types.h>

using namespace std;

vector<string> directories;
vector<string> files;

static bool checkType(string fileName, const vector<string> & permittedExtensions);

Directory::Directory(string dirPath) : dirPath(dirPath) {
  // TODO(cmihail): get this from config file and it should be read only once
  vector<string> permittedExtensions;
  permittedExtensions.push_back("avi");
  permittedExtensions.push_back("mp3");

  // Read directories and permitted files from dirPath.
  DIR * directory = opendir(dirPath.c_str());
  if (directory != NULL) {
    struct dirent * dirEntry = readdir(directory);
    while (dirEntry) {
      // Doesn't save hidden files.
      if (dirEntry->d_name[0] != '.') {
        if (dirEntry->d_type == DT_DIR) {
          directories.push_back(dirEntry->d_name);
        } else if (checkType(dirEntry->d_name, permittedExtensions)) {
          files.push_back(dirEntry->d_name);
        }
      }
      dirEntry = readdir(directory);
    }
    closedir(directory);
  } else {
    stringstream out;
    out << "Problem at opening " << dirPath;
    Logger::print(__FILE__, __LINE__, Logger::WARNING, out.str());
  }
}

vector<string> Directory::getDirectories() {
  return directories;
}

vector<string> Directory::getFiles() {
  return files;
}

/**
 * Checks if a file has a permitted extension.
 */
static bool checkType(string fileName, const vector<string> & permittedExtensions) {
  // Get the type of the file.
  bool hasType = false;
  string extension = "";
  for (int i = fileName.length() - 1; i >= 0; i--) {
    if (fileName[i] != '.') {
      extension += fileName[i];
    } else {
      hasType = true;
      break;
    }
  }

  // Tests if the extension is permitted.
  if (hasType) {
    reverse(extension.begin(), extension.end());
    for (int i = 0, limit = permittedExtensions.size(); i < limit; i++) {
      if (extension.compare(permittedExtensions[i]) == 0) {
        return true;
      }
    }
  }
  return false;
}


