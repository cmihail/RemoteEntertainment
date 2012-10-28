package player;

/**
 * Defines the handler for player commands.
 *
 * @author cmihail (Mihail Costea)
 */
public interface PlayerCommandHandler {
  /**
   * Defines actions to be performed on setting the position in the media which is playing.
   * @param position the position in [0-1] interval which was set
   */
  void onSetPosition(float position);

  /**
   * Defines actions to be performed on previous.
   */
  void onPrevious();

  /**
   * Defines actions to be performed on to next.
   */
  void onNext();

  /**
   * Defines actions to be performed on backward.
   */
  void onBackward();

  /**
   * Defines actions to be performed on forward.
   */
  void onForward();

  /**
   * Defines actions to be performed on stopping the media.
   */
  void onStop();

  /**
   * Defines actions to be performed on playing the media.
   */
  void onPlay();

  /**
   * Defines actions to be performed on pausing the media.
   */
  void onPause();

  /**
   * Defines actions to be performed on mute.
   */
  void onMute();

  /**
   * Defines actions to be performed on setting the volume.
   * @param value the value that was set
   */
  void onSetVolume(int value);

  /**
   * Defines actions to be performed on toggle full screen.
   */
  void onToggleFullScreen();
}
