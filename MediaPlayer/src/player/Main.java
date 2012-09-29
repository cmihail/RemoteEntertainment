package player;

import javax.swing.SwingUtilities;

import client.Client;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			// TODO(cmihail): just for dev, delete when not needed any more
			System.err.println("Usage: ./Player <path_to_movie> <port>");
			System.exit(1);
		}
		final String pathToMovie = args[0];

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
