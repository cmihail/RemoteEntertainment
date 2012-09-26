package player;

import java.io.IOException;
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
	private final SocketChannel socketChannel; // TODO(cmihail): move this outside this class
	private PlayerModel playerModel;

	public Player(SocketChannel socketChannel) {
	  this.socketChannel = socketChannel;

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
			  try {
//			    byte[] bytes = new byte[4];
//			    socketChannel.socket().getInputStream().read(bytes, 0, 4);
//			    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//			    byteBuffer.order(ByteOrder.LITTLE_ENDIAN); // TODO error with this, change from server
//			    int numOfBytes = byteBuffer.getInt();
//			    System.out.println("[Player]: Size: " + numOfBytes);

			    CodedInputStream codedInputStream =
			        CodedInputStream.newInstance(socketChannel.socket().getInputStream());
			    Command command = Command.parseFrom(codedInputStream);
			    // Execute command. TODO(cmihail): elaborate
			    if (command.getType() == Type.PLAY) {
			      playerModel.getMediaPlayer().play();
			    } else if (command.getType() == Type.PAUSE) {
			      playerModel.getMediaPlayer().pause();
			    } else {
			      System.out.println("Wrong command!!!");
			    }
        } catch (IOException e) {
          // TODO: handle exception
          System.out.println("ERR: " + e.getMessage());
          System.exit(1);
        }


//				// TODO(cmihail): change this with proto file
//			  try {
//			    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//
//			    String line;
//	        while ((line = reader.readLine()) != null) {
//	          // TODO(cmihail): use logger + error at reading with a plus 1 char
//	          // (maybe from "\n" sent by server
//	          System.out.println("[CLIENT]: " + line + " " + line.length());
//
//	          if ("play".equals(line.substring(1))) { // TODO(cmihail): synchronize and use PlayerModel
//	            playerModel.getMediaPlayer().play();
//	          } else if ("pause".equals(line.substring(1))) {
//	            playerModel.getMediaPlayer().pause();
//	          }
//	        }
//	        System.err.println("Not Good"); // TODO(cmihail): use logger
//        } catch (Exception e) {
//          // TODO: handle exception
//          System.err.println(e.getMessage()); // TODO(cmihail): use logger
//        }
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
				sendCommand(Command.Type.START_MOVIE, pathToMovie);
			}

			@Override
			public void onSetVolume(int value) {
				sendCommand(Command.Type.SET_VOLUME, value + "");
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

	private void sendCommand(Command.Type type) {
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
//		try { TODO(cmihail): uncomment when the server is ready
//			command.writeTo(out);
//		} catch (IOException e) {
//			System.err.println("IO error: " + e.getMessage()); // TODO(cmihail): use log file
//		} finally { // TODO(cmihail): think about closing <out>
//			try {
//	      out.close();
//      } catch (IOException e) {
//      	System.err.println("IO error: " + e.getMessage()); // TODO(cmihail): use log file
//      }
//		}
	}
}
