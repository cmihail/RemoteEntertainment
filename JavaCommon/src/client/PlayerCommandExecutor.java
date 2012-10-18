package client;

import java.util.logging.Level;
import java.util.logging.Logger;

import logger.CommonLogger;
import client.PlayerCommandHandler;

/**
 * Defines the player command executor which selects the right method from the handler
 * for a given command type.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerCommandExecutor {

  private final Logger logger = CommonLogger.getLogger("Client");
	private final PlayerCommandHandler playerCommandHandler;

	public PlayerCommandExecutor(PlayerCommandHandler playerCommandHandler) {
		this.playerCommandHandler = playerCommandHandler;
	}

	/**
	 * @param command the {@link PlayerCommand} to execute
	 * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
	 */
	public void executeCommand(PlayerCommand command, boolean notifyExecution) {
	  String info = command.getInfo();
    switch (command.getType()) {
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

    case PREVIOUS:
      playerCommandHandler.onPrevious(notifyExecution);
      break;

    case NEXT:
      playerCommandHandler.onNext(notifyExecution);
      break;

    case BACKWARD:
      playerCommandHandler.onBackward(notifyExecution);
      break;

    case FORWARD:
      playerCommandHandler.onForward(notifyExecution);
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
      logger.log(Level.WARNING, "Invalid volume command: " + command.getType().toString());
      break;
    }
	}
}
