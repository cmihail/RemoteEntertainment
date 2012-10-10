package com.cmihail.remoteentertainment;

import proto.ProtoPlayer.Command.Type;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class MainActivity extends Activity {

  private ClientThread clientThread;
  private ImageButton rewindButton;
  private ImageButton forwardButton;
  private ImageButton playPauseButton;
  private ImageButton previousButton;
  private ImageButton nextButton;

  private boolean isPlaying = true; // TODO(cmihail): move this into it's own class

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.player);

    // Start client thread which communicates with the server.
    clientThread = new ClientThread();
    clientThread.start();

    // Assign button.
    rewindButton = (ImageButton) findViewById(R.id.rewindButton);
    forwardButton = (ImageButton) findViewById(R.id.forwardButton);
    playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
    previousButton = (ImageButton) findViewById(R.id.previousButton);
    nextButton = (ImageButton) findViewById(R.id.nextButton);

    // Bind listeners to buttons.
    bindListeners();
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
    clientThread.exit();
  }

  public void bindListeners() {
    imageButtonAction(rewindButton, Type.REWIND); // TODO(cmihail): Maybe backward instead of rewind
    imageButtonAction(forwardButton, Type.FAST_FORWARD); // TODO(cmihail): forward instead of fast forward

    playPauseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isPlaying) {
          clientThread.sendCommand(Type.PAUSE);
          playPauseButton.setImageResource(R.drawable.btn_pause);
        } else {
          clientThread.sendCommand(Type.PAUSE);
          playPauseButton.setImageResource(R.drawable.btn_play);
        }
        isPlaying = !isPlaying;
      }
    });

    imageButtonAction(previousButton, Type.PREVIOUS_CHAPTER); // TODO(cmihail): no chapter
    imageButtonAction(nextButton, Type.NEXT_CHAPTER); // TODO(cmihail): no chapter
  }

  // TODO(cmihail): temporary solution
  public void imageButtonAction(ImageButton button, final Type type) {
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clientThread.sendCommand(type); // TODO(cmihail): keep state of player
      }
    });
  }
}