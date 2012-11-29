/*
 * Directory.h
 *
 *  Created on: Nov 29, 2012
 *      Author: cmihail
 */

#ifndef DIRECTORY_H_
#define DIRECTORY_H_

#include <string>
#include <vector>

class Directory {
public:
  Directory(std::string dirPath);

  /**
   * Gets a vector with all contained directories.
   */
  std::vector<std::string> getDirectories();

  /**
   * Gets a vector with all contained files.
   */
  std::vector<std::string> getFiles();

private:
  std::string dirPath;
};

#endif /* DIRECTORY_H_ */
