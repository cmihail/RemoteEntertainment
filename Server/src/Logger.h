/*
 * Logger.h
 *
 *  Created on: Oct 10, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef LOGGER_H
#define LOGGER_H

#include <string>

class Logger {
public:
  enum Type {
    INFORMATION,
    WARNING,
    ERROR
  };

  static void print(std::string file, int line, Type type, std::string message);

private:
  static std::string typeAsString(Type type);
};

#endif /* LOGGER_H */
