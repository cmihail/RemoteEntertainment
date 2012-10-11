package client;

import java.util.logging.Level;
import java.util.logging.Logger;

import logger.CommonLogger;
import proto.ProtoPlayer.Command.Type;
import client.PlayerCommandHandler;

/**
 * Defines the model for player
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerCommandExecutor {

  private final Logger logger = CommonLogger.getLogger("Client");
	private final PlayerCommandHandler playerCommandHandler;

	public PlayerCommandExecutor(PlayerCommandHandler playerCommandHandler) {
		this.playerCommandHandler = playerCommandHandler;
	}

	// TODO(cmihail): maybe modify type and info with PlayerCommand
	public void executeCommand(Type type, String info, boolean notifyExecution) {
    switch (type) {
    case SET_POSITION:
      if (info == null) {
        logger.log(Level.WARNING, "Invalid position: " + info);
      }
      try {
        playerCommandHandler.onSetPosition(Float.parseFloat(info), notifyExecution);
      } catch (NumberFormatException e) {
        logger.log(Level.WARNING, "Invalid position: " + info);
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
        logger.log(Level.WARNING, "Invalid volume value: " + info);
      }
      try {
        playerCommandHandler.onSetVolume(Integer.parseInt(info), notifyExecution);
      } catch (NumberFormatException e) {
        logger.log(Level.WARNING, "Invalid volume value: " + info);
      }
      break;

    case TOGGLE_FULL_SCREEN:
      playerCommandHandler.onToggleFullScreen(notifyExecution);
      break;

    case START_MOVIE:
      if (info == null) {
        logger.log(Level.WARNING, "Invalid movie: " + info);
      }
      playerCommandHandler.onStartMovie(info);
      break;

    default:
      logger.log(Level.WARNING, "Invalid volume command: " + type.toString());
      break;
    }
	}
}
