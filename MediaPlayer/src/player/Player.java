package player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import proto.ProtoPlayer.Command;
import proto.ProtoPlayer.Command.Information;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class Player {

	private final PlayerView playerView;
	private final InputStream in;
	private final OutputStream out;
	private PlayerModel playerModel;

	public Player(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;

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

	public void bindInputTheard() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO(cmihail): change this with proto file
			  try {
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			    String line;
	        while ((line = reader.readLine()) != null) {
	          // TODO(cmihail): use logger + error at reading with a plus 1 char
	          // (maybe from "\n" sent by server
	          System.out.println("[CLIENT]: " + line + " " + line.length());

	          if ("play".equals(line.substring(1))) { // TODO(cmihail): synchronize and use PlayerModel
	            playerModel.getMediaPlayer().play();
	          } else if ("pause".equals(line.substring(1))) {
	            playerModel.getMediaPlayer().pause();
	            System.out.println("[CLIENT] pause command!");
	          }
	        }
	        System.err.println("Not Good"); // TODO(cmihail): use logger
        } catch (Exception e) {
          // TODO: handle exception
          System.err.println(e.getMessage()); // TODO(cmihail): use logger
          System.exit(1);
        }
			}
		};
		thread.start();
	}

	/**
	 * @return the handler for internal commands
	 */
	public PlayerModel.Handler createModelHandler() {
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
