package com.cmihail.remoteentertainment;

import proto.ProtoPlayer.Command.Type;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

  private final ClientThread clientThread;

  public MainActivity() {
    clientThread = new ClientThread();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    clientThread.start();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }

  public void onPlayPauseButtonClick(View view) {
    clientThread.sendCommand(Type.PAUSE); // TODO(cmihail): keep state of player
  }

  public void onRewindButtonClick(View view) {
    clientThread.sendCommand(Type.REWIND);
  }

  public void onFastForwardButtonClick(View view) {
    clientThread.sendCommand(Type.FAST_FORWARD);
  }

  public void onToggleFullScreenButtonClick(View view) {
    clientThread.sendCommand(Type.TOGGLE_FULL_SCREEN);
  }
}