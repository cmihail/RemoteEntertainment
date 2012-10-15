/*
 * Logger.h
 *
 *  Created on: Oct 10, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef LOGGER_H
#define LOGGER_H

#include <string>

/**
 * Defines the logger used everywhere in the server.
 */
class Logger {
public:
  /**
   * The warning level for a message.
   */
  enum Type {
    INFO,
    WARNING,
    SEVERE
  };

  /**
   * @param file the file where the method is called
   * @param line the line where the method is called
   * @param type the warning level
   * @param message the log message
   */
  static void print(std::string file, int line, Type type, std::string message);

private:
  /**
   * @param type the warning level
   * @return the type as a sting
   */
  static std::string typeAsString(Type type);
};

#endif /* LOGGER_H */
