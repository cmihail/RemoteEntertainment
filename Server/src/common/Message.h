/*
 * Message.h
 *
 *  Created on: Oct 9, 2012
 *      Author: cmihail
 */

#ifndef MESSAGE_H
#define MESSAGE_H

class Message {
  int length;
  char * content;

public:
  Message(int length);
  ~Message();

  bool hasContent();
  int getLength();
  void setContent(const char * content, int length);
  char * getContent();
};

#endif /* MESSAGE_H */
