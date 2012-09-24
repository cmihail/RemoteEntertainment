/**
 * TODO(cmihail): comments
 *
 * Author: cmihail(Mihail Costea)
 */

#ifndef MEDIA_PLAYER_H
#define MEDIA_PLAYER_H

#include <string>

class MediaPlayer {
public:
  void startPlayer(); // TODO(cmihail): maybe in constructor
  void exitPlayer();

  void setPosition(float position);
  void previousChapter();
  void nextChapter();
  void rewind();
  void fastForward();
  void stop();
  void play();
  void pause();
  void mute();
  void setVolume(int value);
  void toggleFullScreen();
  void startMovie(std::string pathToMovie); // TODO(cmihail): only for dev
};

#endif
