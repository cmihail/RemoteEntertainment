package com.cmihail.remoteentertainment;

import player.PlayerCommandExecutor;
import proto.PlayerCommand;
import proto.ProtoPlayer.Command.Type;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

/**
 * Defines the player presenter.
 *
 * @author cmihail (Mihail Costea)
 */
public class Player {

  private final player.PlayerCommandExecutor commandExecutor;
  private boolean isPlaying = true; // TODO(cmihail): move this into it's own class

  private ImageButton rewindButton;
  private ImageButton forwardButton;
  private ImageButton previousButton;
  private ImageButton nextButton;
  private ImageButton playPauseButton;

  private SeekBar mediaProgressBar;

  public Player(Activity activity, PlayerCommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;

    rewindButton = (ImageButton) activity.findViewById(R.id.rewindButton);
    forwardButton = (ImageButton) activity.findViewById(R.id.forwardButton);
    previousButton = (ImageButton) activity.findViewById(R.id.previousButton);
    nextButton = (ImageButton) activity.findViewById(R.id.nextButton);
    playPauseButton = (ImageButton) activity.findViewById(R.id.playPauseButton);

    mediaProgressBar = (SeekBar) activity.findViewById(R.id.mediaProgressBar);;

    // Bind listeners to buttons.
    bindListeners();
  }

  public void bindListeners() {
    onImageButtonClick(rewindButton, Type.BACKWARD);
    onImageButtonClick(forwardButton, Type.FORWARD);
    onImageButtonClick(previousButton, Type.PREVIOUS);
    onImageButtonClick(nextButton, Type.NEXT);

    playPauseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isPlaying) {
          commandExecutor.executeCommand(new PlayerCommand(Type.PAUSE), true);
        } else {
          commandExecutor.executeCommand(new PlayerCommand(Type.PLAY), true);
        }
        isPlaying = !isPlaying;
      }
    });

    mediaProgressBar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO(cmihail)
      }
    });
  }

  /**
   * Executes a command when a image button is clicked.
   * This method is generic (some image buttons have specific actions).
   */
  private void onImageButtonClick(ImageButton button, final Type type) {
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        commandExecutor.executeCommand(new PlayerCommand(type), true);
      }
    });
  }
}
