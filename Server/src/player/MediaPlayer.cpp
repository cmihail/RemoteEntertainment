/**
 * TODO(cmihail): comments
 *
 * Author: cmihail(Mihail Costea)
 */

#include "MediaPlayer.h"
#include "Process.h"

#include <cstdlib>

using namespace std;

Process * mediaPlayerProcess;

void MediaPlayer::startPlayer() {
  // TODO(cmihail): only for dev
  string program = "java";
  string * programArgs = new string[5];
  programArgs[0] = "-cp";
  programArgs[1] = ".:../../MediaPlayer/lib/vlcj-2.1.0.jar:" +
      string("../../MediaPlayer/lib/protobuf-java-2.4.1.jar");
  programArgs[2] = "-Djna.library.path=/Applications/VLC.app/Contents/MacOS/lib";
  programArgs[3] = "player/Main";
  programArgs[4] = "/Users/cmihail/Downloads/Lockout.UNRATED.720p.BluRay.x264-BLOW [PublicHD]" +
      string("/Lockout.UNRATED.720p.BluRay.x264-BLOW.PublicHD.mkv");

  mediaPlayerProcess = new Process(program, 5, programArgs);
}

void MediaPlayer::exitPlayer() {

}

void MediaPlayer::setPosition(float position) {

}

void MediaPlayer::previousChapter() {

}

void MediaPlayer::nextChapter() {

}

void MediaPlayer::rewind() {

}

void MediaPlayer::fastForward() {

}

void MediaPlayer::stop() {

}

void MediaPlayer::play() {

}

void MediaPlayer::pause() {

}

void MediaPlayer::mute() {

}

void MediaPlayer::setVolume(int value) {

}

void MediaPlayer::toggleFullScreen() {

}

void MediaPlayer::startMovie(string pathToMovie) {

}
