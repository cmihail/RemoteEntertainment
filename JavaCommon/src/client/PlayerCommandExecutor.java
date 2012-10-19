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

  private static final Logger logger = CommonLogger.getLogger("Client");

  private final Client client;
	private final PlayerCommandHandler playerCommandHandler;

	/**
	 * @param client the client which sends commands to server
	 * @param playerCommandHandler handler for every player command
	 */
	public PlayerCommandExecutor(Client client, PlayerCommandHandler playerCommandHandler) {
	  this.client = client;
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
        playerCommandHandler.onSetPosition(Float.parseFloat(info));
      } catch (NumberFormatException e) {
        logger.log(Level.WARNING, "Invalid position: " + info);
      }
      break;

    case PREVIOUS:
      playerCommandHandler.onPrevious();
      break;

    case NEXT:
      playerCommandHandler.onNext();
      break;

    case BACKWARD:
      playerCommandHandler.onBackward();
      break;

    case FORWARD:
      playerCommandHandler.onForward();
      break;

    case STOP:
      playerCommandHandler.onStop();
      break;

    case PLAY:
      playerCommandHandler.onPlay();
      break;

    case PAUSE:
      playerCommandHandler.onPause();
      break;

    case MUTE:
      playerCommandHandler.onMute();
      break;

    case SET_VOLUME:
      if (info == null) {
        logger.log(Level.WARNING, "Invalid volume value: " + info);
      }
      try {
        playerCommandHandler.onSetVolume(Integer.parseInt(info));
      } catch (NumberFormatException e) {
        logger.log(Level.WARNING, "Invalid volume value: " + info);
      }
      break;

    case TOGGLE_FULL_SCREEN:
      playerCommandHandler.onToggleFullScreen();
      break;

    case START_MOVIE:
      if (info == null) {
        logger.log(Level.WARNING, "Invalid movie: " + info);
      }
      playerCommandHandler.onStartMovie(info);
      break;

    default:
      logger.log(Level.WARNING, "Invalid volume command: " + command.getType().toString());
      return;
    }

    // Send the command to the server.
    if (notifyExecution) {
      client.sendCommand(command);
    }
	}
}
