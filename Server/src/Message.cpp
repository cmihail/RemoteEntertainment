/*
 * Message.h=cpp
 *
 *  Created on: Oct 9, 2012
 *      Author: cmihail (Mihail Costea)
 *
 * Defines the implementation of Message.h.
 */

#include "Message.h"

#include <cstdlib>
#include <cstring>

Message::Message() {
  length = 0;
  content = NULL;
}

Message::Message(int length) : length(length) {
  if (length <= 0) {
    length = 0;
    content = NULL;
  } else {
    content = new char[length];
    memset(content, 0, length);
  }
}

Message::~Message() {
  if (hasContent()) {
    delete content;
  }
}

bool Message::hasContent() {
  if (content == NULL) {
    return false;
  }
  return true;
}

int Message::getLength() {
  return length;
}

void Message::setContent(const char * content, int length) {
  int min = this->length < length ? this->length : length;
  memcpy(this->content, content, min);
}

char * Message::getContent() {
  return content;
}
