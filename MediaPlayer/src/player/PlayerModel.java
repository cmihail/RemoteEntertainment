package player;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerModel {

	private static final int SKIP_TIME_MS = 10 * 1000;

	/**
	 * Defines the handler for every action executed on the {@link Player},
	 * that was called within this application.
	 *
	 * @author cmihail (Mihail Costea)
	 */
	public interface Handler {
		void onStartMovie(String pathToMovie); // TODO(cmihail): only for dev, must use another name
		void onSetPosition(float position);
		void onPreviousChapter();
		void onNextChapter();
		void onRewind();
		void onFastForward();
		void onStop();
		void onPlay();
		void onPause();
		void onMute();
		void onSetVolume(int value);
		void onToggleFullScreen();
	}

	private final EmbeddedMediaPlayer mediaPlayer;
	private final Handler playerHandler;

	public PlayerModel(EmbeddedMediaPlayer mediaPlayer, Handler playerHandler) {
		this.mediaPlayer = mediaPlayer;
		this.playerHandler = playerHandler;
	}

	public void startMovie(String pathToMovie) {
		mediaPlayer.startMedia(pathToMovie);
		playerHandler.onStartMovie(pathToMovie);
	}

	public void setPosition(float position) {
		mediaPlayer.setPosition(position);
		playerHandler.onSetPosition(position);
	}

	public void previousChapter() {
		mediaPlayer.previousChapter();
		playerHandler.onPreviousChapter();
	}

	public void nextChapter() {
		mediaPlayer.nextChapter();
		playerHandler.onNextChapter();
	}

	public void rewindButton() {
		mediaPlayer.skip(-SKIP_TIME_MS);
		playerHandler.onRewind();
	}

	public void fastForward() {
		mediaPlayer.skip(SKIP_TIME_MS);
		playerHandler.onFastForward();
	}

	public void stop() {
		mediaPlayer.stop();
		playerHandler.onStop();
	}

	public void play() {
		mediaPlayer.play();
		playerHandler.onPlay();
	}

	public void pause() {
		mediaPlayer.pause();
		playerHandler.onPause();
	}

	public void mute() {
		mediaPlayer.mute();
		playerHandler.onMute();
	}

	public void setVolume(int value) {
		mediaPlayer.setVolume(value);
		playerHandler.onSetVolume(value);
	}

	public void toggleFullScreen() {
		mediaPlayer.toggleFullScreen();
		playerHandler.onToggleFullScreen();
	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
}
