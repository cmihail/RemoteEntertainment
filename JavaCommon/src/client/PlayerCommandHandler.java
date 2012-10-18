package client;

/**
 * Defines the handler for player commands.
 * TODO(cmihail): explain notifyExecution param
 *
 * @author cmihail (Mihail Costea)
 */
public interface PlayerCommandHandler {
  void onStartMovie(String pathToMovie); // TODO(cmihail): only for dev, must use another name

  /**
   * Defines actions to be performed on setting the position in the media which is playing.
   * @param position the position in [0-1] interval which was set
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onSetPosition(float position, boolean notifyExecution);

  /**
   * Defines actions to be performed on previous.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onPrevious(boolean notifyExecution);

  /**
   * Defines actions to be performed on to next.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onNext(boolean notifyExecution);

  /**
   * Defines actions to be performed on backward.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onBackward(boolean notifyExecution);

  /**
   * Defines actions to be performed on forward.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onForward(boolean notifyExecution);

  /**
   * Defines actions to be performed on stopping the media.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onStop(boolean notifyExecution);

  /**
   * Defines actions to be performed on playing the media.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onPlay(boolean notifyExecution);

  /**
   * Defines actions to be performed on pausing the media.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onPause(boolean notifyExecution);

  /**
   * Defines actions to be performed on mute.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onMute(boolean notifyExecution);

  /**
   * Defines actions to be performed on setting the volume.
   * @param value the value that was set
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onSetVolume(int value, boolean notifyExecution);

  /**
   * Defines actions to be performed on toggle full screen.
   * @param notifyExecution true if the current program should notify others about the execution
   * of the command or not
   */
  void onToggleFullScreen(boolean notifyExecution);
}
