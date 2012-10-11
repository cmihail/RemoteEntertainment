package player;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import client.Client;
import logger.CommonLogger;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class Main {

  public static final Logger logger = CommonLogger.getLogger("MediaPlayer");

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.log(Level.SEVERE, "Execute as ./PlayerMain <path_to_movie> <port>");
		}
		final String pathToMovie = args[0]; // TODO(cmihail): just for dev, delete when not needed

		// Create a client to connect to server.
		final Client client = new Client();
		client.connect("localhost", Integer.parseInt(args[1]));

		// Create media player.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			  Player player = new Player(client);
        player.startMovie(pathToMovie);
			}
		});
	}
}
