package player;

import proto.Client;
import proto.PlayerCommand;
import proto.ProtoPlayer.Command.Type;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class Player {

  private final PlayerView playerView;
  private final Client client;
  private PlayerModel playerModel;

  public Player(Client client) {
    this.client = client;

    playerView = new PlayerView(new PlayerView.Handler() {
      @Override
      public void onCreatePlayerModel(PlayerModel playerModel) {
        Player.this.playerModel = playerModel;
      }
    }, createModelHandler());

    bindInputTheard();
  }

  /**
   * @return the model associated with this player
   */
  public PlayerModel getModel() {
    return playerModel;
  }

  // TODO(cmihail): notify main thread if this has failed
  private void bindInputTheard() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        while (true) {
          PlayerCommand playerCommmand = client.recvCommand();

          // Execute command. TODO(cmihail): elaborate
          if (playerCommmand.getType() == Type.PLAY) {
            playerModel.getMediaPlayer().play();
          } else if (playerCommmand.getType() == Type.PAUSE) {
            playerModel.getMediaPlayer().pause();
          } else {
            System.out.println("[PLAYER]: Wrong command!!!"); // TODO(cmihail): use logger
          }
        }
      }
    };
    thread.start();
  }

  /**
   * @return the handler for internal commands
   */
  private PlayerModel.Handler createModelHandler() {
    return new PlayerModel.Handler() {

      @Override
      public void onToggleFullScreen() {
        client.sendCommand(new PlayerCommand(Type.TOGGLE_FULL_SCREEN));
      }

      @Override
      public void onStop() {
        client.sendCommand(new PlayerCommand(Type.STOP));
      }

      @Override
      public void onStartMovie(String pathToMovie) {
        // TODO(cmihail)
      }

      @Override
      public void onSetVolume(int value) {
        // TODO(cmihail)
      }

      @Override
      public void onSetPosition(float position) {
        client.sendCommand(new PlayerCommand(Type.SET_POSITION, position + ""));
      }

      @Override
      public void onRewind() {
        client.sendCommand(new PlayerCommand(Type.REWIND));
      }

      @Override
      public void onPreviousChapter() {
        client.sendCommand(new PlayerCommand(Type.PREVIOUS_CHAPTER));
      }

      @Override
      public void onPlay() {
        client.sendCommand(new PlayerCommand(Type.PLAY));
      }

      @Override
      public void onPause() {
        client.sendCommand(new PlayerCommand(Type.PAUSE));
      }

      @Override
      public void onNextChapter() {
        client.sendCommand(new PlayerCommand(Type.NEXT_CHAPTER));
      }

      @Override
      public void onMute() {
        client.sendCommand(new PlayerCommand(Type.MUTE));
      }

      @Override
      public void onFastForward() {
        client.sendCommand(new PlayerCommand(Type.FAST_FORWARD));
      }
    };
  }
}
