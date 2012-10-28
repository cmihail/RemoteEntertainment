package player;

import java.util.logging.Level;

import logger.CommonLogger;
import proto.PlayerCommand;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import client.Client;
import client.Client.ConnectionLostException;

/**
 * Defines the player presenter.
 *
 * @author cmihail (Mihail Costea)
 */
public class Player {

  private static final int SKIP_TIME_MS = 10 * 1000;

  private final Client client;
  private final PlayerCommandExecutor commandExecutor;
  private final PlayerView playerView;

  public Player(Client client) {
    this.client = client;
    commandExecutor = new PlayerCommandExecutor(client, createCommandHandler());
    playerView = new PlayerView(commandExecutor);

    receiveCommandsFromServer();
  }

  public void startMovie(String pathToMovie) { // TODO(cmihail): only for dev
    playerView.getMediaPlayer().playMedia(pathToMovie);
  }

  private void receiveCommandsFromServer() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        while (true) {
          final PlayerCommand playerCommmand;
          try {
            playerCommmand = new PlayerCommand(client.read());
          } catch (ConnectionLostException e) {
            CommonLogger.log(Level.WARNING, e.getMessage());
            break;
          }

          // Execute command.
          commandExecutor.executeCommand(playerCommmand, false);
        }
      }
    };
    thread.start();
  }

  /**
   * @return the handler for player commands
   */
  private PlayerCommandHandler createCommandHandler() {
    return new PlayerCommandHandler() {

      @Override
      public void onToggleFullScreen() {
        EmbeddedMediaPlayer mediaPlayer = playerView.getMediaPlayer();
        playerView.prepareForFullScreen(!mediaPlayer.isFullScreen());
        mediaPlayer.toggleFullScreen();
      }

      @Override
      public void onStop() {
        playerView.getMediaPlayer().stop();
      }

      @Override
      public void onSetVolume(int value) {
        playerView.getMediaPlayer().setVolume(value);
      }

      @Override
      public void onSetPosition(float position) {
        playerView.getMediaPlayer().setPosition(position);
      }

      @Override
      public void onBackward() {
        playerView.getMediaPlayer().skip(-SKIP_TIME_MS);
      }

      @Override
      public void onPrevious() {
        playerView.getMediaPlayer().previousChapter();
      }

      @Override
      public void onPlay() {
        playerView.getMediaPlayer().play();
      }

      @Override
      public void onPause() {
        playerView.getMediaPlayer().pause();
      }

      @Override
      public void onNext() {
        playerView.getMediaPlayer().nextChapter();
      }

      @Override
      public void onMute() {
        playerView.getMediaPlayer().mute();
      }

      @Override
      public void onForward() {
        playerView.getMediaPlayer().skip(SKIP_TIME_MS);
      }
    };
  }
}
