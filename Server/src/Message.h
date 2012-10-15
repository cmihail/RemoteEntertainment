/*
 * Message.h
 *
 *  Created on: Oct 9, 2012
 *      Author: cmihail (Mihail Costea)
 */

#ifndef MESSAGE_H
#define MESSAGE_H

/**
 * Defines the message that contains data exchanged between server - client.
 */
class Message {
  int length;
  char * content;

public:
  /**
   * Defines a message with no content.
   */
  Message();

  /**
   * @param length the maximum length of the message
   */
  Message(int length);

  ~Message();

  /**
   * @return true if the message can contain at least 1 character
   */
  bool hasContent();

  /**
   * @return the length of the message
   */
  int getLength();

  /**
   * @param content the new content of the message
   * @param length the length of the content
   */
  void setContent(const char * content, int length);

  /**
   * @return the content of the message as a C string
   */
  char * getContent();
};

#endif /* MESSAGE_H */
