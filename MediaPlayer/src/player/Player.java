package player;

import proto.ProtoPlayer.Command.Type;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import client.Client;
import client.PlayerCommand;
import client.PlayerCommandExecutor;
import client.PlayerCommandHandler;

/**
 * TODO(cmihail): comments
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
    commandExecutor = new PlayerCommandExecutor(createCommandHandler());
    playerView = new PlayerView(commandExecutor);

    bindExternalCommandsThread();
  }

  public void startMovie(String pathToMovie) { // TODO(cmihail): only for dev
    playerView.getMediaPlayer().playMedia(pathToMovie);
  }

  // TODO(cmihail): notify main thread if this has failed
  private void bindExternalCommandsThread() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        while (true) {
          PlayerCommand playerCommmand = client.receiveCommand();

          // Execute command.
          commandExecutor.executeCommand(playerCommmand.getType(),
              playerCommmand.getInfo(), false);
        }
      }
    };
    thread.start();
  }

  private PlayerCommandHandler createCommandHandler() {
    return new PlayerCommandHandler() {

      @Override
      public void onToggleFullScreen(boolean notifyExecution) {
        EmbeddedMediaPlayer mediaPlayer = playerView.getMediaPlayer();
        playerView.prepareForFullScreen(!mediaPlayer.isFullScreen());
        mediaPlayer.toggleFullScreen();
        sendCommand(Type.TOGGLE_FULL_SCREEN, notifyExecution);
      }

      @Override
      public void onStop(boolean notifyExecution) {
        playerView.getMediaPlayer().stop();
        sendCommand(Type.STOP, notifyExecution);
      }

      @Override
      public void onStartMovie(String notifyExecution) {
        // TODO(cmihail): delete this method from interface
      }

      @Override
      public void onSetVolume(int value, boolean notifyExecution) {
        playerView.getMediaPlayer().setVolume(value);
        // TODO(cmihail)
      }

      @Override
      public void onSetPosition(float position, boolean notifyExecution) {
        playerView.getMediaPlayer().setPosition(position);
        sendCommand(Type.SET_POSITION, position + "", notifyExecution);
      }

      @Override
      public void onRewind(boolean notifyExecution) {
        playerView.getMediaPlayer().skip(-SKIP_TIME_MS);
        sendCommand(Type.REWIND, notifyExecution);
      }

      @Override
      public void onPreviousChapter(boolean notifyExecution) {
        playerView.getMediaPlayer().previousChapter();
        sendCommand(Type.PREVIOUS_CHAPTER, notifyExecution);
      }

      @Override
      public void onPlay(boolean notifyExecution) {
        playerView.getMediaPlayer().play();
        sendCommand(Type.PLAY, notifyExecution);
      }

      @Override
      public void onPause(boolean notifyExecution) {
        playerView.getMediaPlayer().pause();
        sendCommand(Type.PAUSE, notifyExecution);
      }

      @Override
      public void onNextChapter(boolean notifyExecution) {
        playerView.getMediaPlayer().nextChapter();
        sendCommand(Type.NEXT_CHAPTER, notifyExecution);
      }

      @Override
      public void onMute(boolean notifyExecution) {
        playerView.getMediaPlayer().mute();
        sendCommand(Type.MUTE, notifyExecution);
      }

      @Override
      public void onFastForward(boolean notifyExecution) {
        playerView.getMediaPlayer().skip(SKIP_TIME_MS);
        sendCommand(Type.FAST_FORWARD, notifyExecution);
      }
    };
  }

  private void sendCommand(Type type, boolean shouldSend) {
    if (shouldSend) {
      client.sendCommand(new PlayerCommand(type));
    }
  }

  private void sendCommand(Type type, String info, boolean shouldSend) {
    if (shouldSend) {
      client.sendCommand(new PlayerCommand(type, info));
    }
  }
}
