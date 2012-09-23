package player;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

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
				Scanner scanner = new Scanner(in);
				while (true) {
					if (!scanner.hasNext()) {
						break;
					}
					String line = scanner.nextLine();
					if (line.equals("play")) { // synchronize and use PlayerModel
						playerModel.getMediaPlayer().play();
					} else if (line.equals("pause")) {
						playerModel.getMediaPlayer().pause();
					}
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
