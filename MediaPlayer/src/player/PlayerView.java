package player;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class PlayerView {

	public interface Handler {
		void onCreatePlayerModel(PlayerModel playerModel);
	}

	private boolean isFullScreen;

	private final Canvas canvas;
	private final JFrame frame;
	private final PlayerControlsPanel playerControlsPanel;
	private final PlayerModel playerModel;

	public PlayerView(Handler viewHandler, PlayerModel.Handler modelHandler) {
		// Create main frame that will contain the media player and the controls panel
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(100, 100); // TODO(cmihail): do not use hard coded variables
		frame.setSize(1050, 600);
		frame.setUndecorated(true); // TODO(cmihail): native buttons do not disappear in fullscreen
		frame.setVisible(true);


		// Create the media player and its model
		canvas = new Canvas();
		frame.add(canvas, BorderLayout.CENTER);

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		FullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(frame);
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);
		playerModel = new PlayerModel(mediaPlayer, modelHandler);
		viewHandler.onCreatePlayerModel(playerModel);

		// Create player controls panel
		playerControlsPanel = new PlayerControlsPanel(playerModel);
		frame.add(playerControlsPanel, BorderLayout.SOUTH);

		canvas.addMouseListener(createMouseListener());
	}

	/**
	 * Update view and player controls panel.
	 */
	public void update() {
		final boolean isFullScreen = playerModel.getMediaPlayer().isFullScreen();
		if (PlayerView.this.isFullScreen != isFullScreen) {
			enableFullScreen(isFullScreen);
		}
	}

	private void enableFullScreen(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				isFullScreen = enabled;
				playerControlsPanel.setVisible(!enabled);
			}
		});
	}

	/**
	 * Creates mouse listener for entering/exiting in full screen mode.
	 */
	private MouseListener createMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					enableFullScreen(!playerModel.getMediaPlayer().isFullScreen());
					playerModel.toggleFullScreen();
				}
			}
		};
	}
}
