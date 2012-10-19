package com.cmihail.remoteentertainment;

import client.Client;
import client.PlayerCommandExecutor;
import client.PlayerCommandHandler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.SeekBar;

/**
 * Defines the main activity of the remote player.
 *
 * @author cmihail (Mihail Costea)
 */
public class MainActivity extends Activity {

  private Client client;
  private Player player;
  private PlayerCommandExecutor commandExecutor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.player);

    // Create a new client and connect to server.
    client = new Client();
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        client.connect("192.168.2.12", 10000);
      }
    });
    thread.start();

    // Create the player command executor.
    commandExecutor = new PlayerCommandExecutor(client, createCommandHandler());

    // Create a player to handle plater media player specific actions.
    player = new Player(this, commandExecutor);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // TODO(cmihail): save clientThread state
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // TODO(cmihail): maybe use alternative for this workaround
    client.disconnect();
  }

  /**
   * @return the handler for player commands
   */
  private PlayerCommandHandler createCommandHandler() {
    return new PlayerCommandHandler() {

      private ImageButton playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
      private SeekBar mediaProgressBar = (SeekBar) findViewById(R.id.mediaProgressBar);

      @Override
      public void onBackward() {
        // Nothing to do.
      }

      @Override
      public void onForward() {
        // Nothing to do.
      }

      @Override
      public void onMute() {
        // TODO Auto-generated method stub
      }

      @Override
      public void onNext() {
        // Nothing to do.
      }

      @Override
      public void onPause() {
        playPauseButton.setImageResource(R.drawable.btn_pause);
      }

      @Override
      public void onPlay() {
        playPauseButton.setImageResource(R.drawable.btn_play);
      }

      @Override
      public void onPrevious() {
        // Nothing to do.
      }

      @Override
      public void onSetPosition(float position) {
        mediaProgressBar.setProgress(Math.round(position * 100));
      }

      @Override
      public void onSetVolume(int arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onStartMovie(String arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onStop() {
        // TODO Auto-generated method stub

      }

      @Override
      public void onToggleFullScreen() {
        // TODO Auto-generated method stub
      }
    };
  }
}