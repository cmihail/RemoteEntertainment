package com.cmihail.remoteentertainment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

  private ClientThread clientThread;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    clientThread = new ClientThread();
    clientThread.start();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }

  public void onPlayButtonClick(View view) {
    System.out.println("play");
    clientThread.getServerCallback().onPause(); // TODO(cmihail): play/pause button instead
  }

  public void onRewindButtonClick(View view) {
    clientThread.getServerCallback().onRewind();
    System.out.println("rewind");
  }

  public void onFastForwardButtonClick(View view) {
    clientThread.getServerCallback().onFastForward();
    System.out.println("fast forward");
  }
}