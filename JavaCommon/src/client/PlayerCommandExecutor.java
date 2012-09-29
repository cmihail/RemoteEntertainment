package client;

import proto.ProtoPlayer.Command.Type;
import client.PlayerCommandHandler;

/**
 * Defines the model for player
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerCommandExecutor {

	private final PlayerCommandHandler playerCommandHandler;

	public PlayerCommandExecutor(PlayerCommandHandler playerCommandHandler) {
		this.playerCommandHandler = playerCommandHandler;
	}

	// TODO(cmihail): maybe modify type and info with PlayerCommand
	public void executeCommand(Type type, String info, boolean notifyExecution) {
    switch (type) {
    case SET_POSITION:
      if (info == null) {
        System.out.println("[Client] Invalid position: " + info); // TODO(cmihail) use logger
      }
      try {
        playerCommandHandler.onSetPosition(Float.parseFloat(info), notifyExecution);
      } catch (NumberFormatException e) {
        System.out.println("[Client] Invalid position: " + info); // TODO(cmihail) use logger
      }
      break;

    case PREVIOUS_CHAPTER:
      playerCommandHandler.onPreviousChapter(notifyExecution);
      break;

    case NEXT_CHAPTER:
      playerCommandHandler.onNextChapter(notifyExecution);
      break;

    case REWIND:
      playerCommandHandler.onRewind(notifyExecution);
      break;

    case FAST_FORWARD:
      playerCommandHandler.onFastForward(notifyExecution);
      break;

    case STOP:
      playerCommandHandler.onStop(notifyExecution);
      break;

    case PLAY:
      playerCommandHandler.onPlay(notifyExecution);
      break;

    case PAUSE:
      playerCommandHandler.onPause(notifyExecution);
      break;

    case MUTE:
      playerCommandHandler.onMute(notifyExecution);
      break;

    case SET_VOLUME:
      if (info == null) {
        System.out.println("[Client] Invalid volume value: " + info); // TODO(cmihail) use logger
      }
      try {
        playerCommandHandler.onSetVolume(Integer.parseInt(info), notifyExecution);
      } catch (NumberFormatException e) {
        System.out.println("[Client] Invalid volume value: " + info); // TODO(cmihail) use logger
      }
      break;

    case TOGGLE_FULL_SCREEN:
      playerCommandHandler.onToggleFullScreen(notifyExecution);
      break;

    case START_MOVIE:
      if (info == null) {
        System.out.println("[Client] Invalid movie: " + info); // TODO(cmihail) use logger
      }
      playerCommandHandler.onStartMovie(info);
      break;

    default:
      System.out.println("[Client] Invalid command: " + type.toString());
      break;
    }
	}
}
