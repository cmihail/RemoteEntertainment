/**
 * TODO(cmihail): comments
 *
 * Author: cmihail(Mihail Costea)
 */

#include "../player/MediaPlayer.h"

#include <iostream>
#include <string>
#include <sys/types.h>
#include <sys/event.h>
#include <sys/time.h>

using namespace std;

int main() {
  MediaPlayer mediaPlayer;
  mediaPlayer.startPlayer();

  string command;

  while(true) {
    getline(cin, command);
    cout << command << "\n";
  }
}
