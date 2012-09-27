package player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.google.protobuf.CodedInputStream;
import proto.ProtoPlayer.Command;
import proto.ProtoPlayer.Command.Information;
import proto.ProtoPlayer.Command.Type;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class Player {

  private final PlayerView playerView;
  private final InputStream inputStream;
  private final OutputStream outputStream;
  private final SocketChannel socketChannel;
  private PlayerModel playerModel;

  public Player(SocketChannel socketChannel, InputStream inputStream, OutputStream outputStream) {
    this.socketChannel = socketChannel;
    this.inputStream = inputStream;
    this.outputStream = outputStream;

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
          try {
            CodedInputStream codedInputStream = CodedInputStream.newInstance(inputStream);
            int varint32 = codedInputStream.readInt32();
            byte[] bytes = codedInputStream.readRawBytes(varint32);
            Command command = Command.parseFrom(bytes);

            // Execute command. TODO(cmihail): elaborate
            if (command.getType() == Type.PLAY) {
              playerModel.getMediaPlayer().play();
            } else if (command.getType() == Type.PAUSE) {
              playerModel.getMediaPlayer().pause();
            } else {
              System.out.println("[PLAYER]: Wrong command!!!"); // TODO(cmihail): use logger
            }
          } catch (IOException e) {
            System.out.println("ERR: " + e.getMessage()); // TODO(cmihail): use logger
            System.exit(1);
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
        sendCommand(Command.Type.TOGGLE_FULL_SCREEN);
      }

      @Override
      public void onStop() {
        sendCommand(Command.Type.STOP);
      }

      @Override
      public void onStartMovie(String pathToMovie) {
//        sendCommand(Command.Type.START_MOVIE, pathToMovie); TODO
      }

      @Override
      public void onSetVolume(int value) {
//        sendCommand(Command.Type.SET_VOLUME, value + ""); TODO
      }

      @Override
      public void onSetPosition(float position) {
        sendCommand(Command.Type.SET_POSITION, position + "");
      }

      @Override
      public void onRewind() {
        sendCommand(Command.Type.REWIND);
      }

      @Override
      public void onPreviousChapter() {
        sendCommand(Command.Type.PREVIOUS_CHAPTER);
      }

      @Override
      public void onPlay() {
        sendCommand(Command.Type.PLAY);
      }

      @Override
      public void onPause() {
        sendCommand(Command.Type.PAUSE);
      }

      @Override
      public void onNextChapter() {
        sendCommand(Command.Type.NEXT_CHAPTER);
      }

      @Override
      public void onMute() {
        sendCommand(Command.Type.MUTE);
      }

      @Override
      public void onFastForward() {
        sendCommand(Command.Type.FAST_FORWARD);
      }
    };
  }

  private void sendCommand(Command.Type type) { // TODO(cmihail): move this into a common class
    Command command = Command.newBuilder()
        .setType(type)
        .build();
    writeCommand(command);
  }

  private void sendCommand(Command.Type type, String info) {
    Command command = Command.newBuilder()
        .setType(type)
        .setInfo(Information.newBuilder().setValue(info).build())
        .build();
    writeCommand(command);
  }

  private void writeCommand(Command command) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      command.writeDelimitedTo(out);

      ByteBuffer commandBuffer = ByteBuffer.wrap(out.toByteArray());
      while (commandBuffer.hasRemaining()) {
        socketChannel.write(commandBuffer);
      }
      System.out.println("[Player] Command sent: " + command.getType().toString());
    } catch (IOException e) {
      System.out.println("[Player] Command error"); // TODO(cmihail): use logger
      e.printStackTrace();
    }
  }
}
