package player;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import proto.ProtoPlayer.Command.Type;

import client.PlayerCommand;
import client.PlayerCommandExecutor;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 * Defines the view for {@link Player} which consists in the player itself
 * and the {@link PlayerControlsPanel}.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerView {

	private final Canvas canvas;
	private final JFrame frame;
	private final EmbeddedMediaPlayer mediaPlayer;
	private final PlayerControlsPanel playerControlsPanel;
	private final PlayerCommandExecutor commandExecutor;

	public PlayerView(PlayerCommandExecutor commandExecutor) {
	  this.commandExecutor = commandExecutor;

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
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer(fullScreenStrategy);
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

		// Create player controls panel
		playerControlsPanel = new PlayerControlsPanel(commandExecutor, mediaPlayer);
		frame.add(playerControlsPanel, BorderLayout.SOUTH);

		canvas.addMouseListener(createMouseListener());
	}

	/**
	 * @return the media player associated with this view
	 */
	public EmbeddedMediaPlayer getMediaPlayer() {
	  return mediaPlayer;
	}

	/**
	 * @param enabled true if view should prepare for full screen, or false if it should
	 * prepare for normal state
	 */
	public void prepareForFullScreen(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
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
				  prepareForFullScreen(!mediaPlayer.isFullScreen());
					commandExecutor.executeCommand(new PlayerCommand(Type.TOGGLE_FULL_SCREEN), true);
				}
			}
		};
	}
}
