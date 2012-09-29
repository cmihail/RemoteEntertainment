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
   */
  void onSetPosition(float position, boolean notifyExecution);

  /**
   * Defines actions to be performed on changing to previous chapter.
   */
  void onPreviousChapter(boolean notifyExecution);

  /**
   * Defines actions to be performed on changing to next chapter.
   */
  void onNextChapter(boolean notifyExecution);

  /**
   * Defines actions to be performed on rewind.
   */
  void onRewind(boolean notifyExecution);

  /**
   * Defines actions to be performed on fast forward.
   */
  void onFastForward(boolean notifyExecution);

  /**
   * Defines actions to be performed on stopping the media.
   */
  void onStop(boolean notifyExecution);

  /**
   * Defines actions to be performed on playing the media.
   */
  void onPlay(boolean notifyExecution);

  /**
   * Defines actions to be performed on pausing the media.
   */
  void onPause(boolean notifyExecution);

  /**
   * Defines actions to be performed on mute.
   */
  void onMute(boolean notifyExecution);

  /**
   * Defines actions to be performed on setting the volume.
   * @param value the value that was set
   */
  void onSetVolume(int value, boolean notifyExecution);

  /**
   * Defines actions to be performed on toggle full screen.
   */
  void onToggleFullScreen(boolean notifyExecution);
}
