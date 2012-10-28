package com.cmihail.remoteentertainment;

import java.util.logging.Level;

import logger.CommonLogger;
import player.PlayerCommandExecutor;
import player.PlayerCommandHandler;
import proto.PlayerCommand;
import proto.PlayerMessageHeader;
import proto.ProtoPlayer.MessageHeader.Type;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.SeekBar;
import client.Client.ConnectionLostException;

/**
 * Defines the main activity of the remote player.
 *
 * @author cmihail (Mihail Costea)
 */
public class MainActivity extends Activity {

  private AndroidClient client;
  private Player player;
  private PlayerCommandExecutor commandExecutor;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.player);

    // Create a new client, connect to server and listen for incoming commands.
    client = new AndroidClient();
    client.connect("192.168.2.8", 10000); // TODO(cmihail): ip in settings

    // Create the player command executor.
    commandExecutor = new PlayerCommandExecutor(client, createCommandHandler());

    // Create a player to handle plater media player specific actions.
    player = new Player(this, commandExecutor);

    receiveCommandsFromServer();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    client.disconnect();
  }

  /**
   * Receives commands from server in an infinite loop.
   */
  private void receiveCommandsFromServer() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while(true) {

          final PlayerCommand command;
          try {
            PlayerMessageHeader header = new PlayerMessageHeader(client.read());
            if (header.getType() == Type.COMMAND) {
              command = new PlayerCommand(client.read());
            } else {
              CommonLogger.log(Level.SEVERE, "Something else then a command was sent");
              break;
            }
          } catch (ConnectionLostException e) {
            CommonLogger.log(Level.SEVERE, e.getMessage());
            break;
          }

          MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              commandExecutor.executeCommand(command, false);
            }
          });
        }
      }
    });
    thread.start();
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
