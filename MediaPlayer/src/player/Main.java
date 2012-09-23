package player;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		if (args.length != 1) {
			// TODO(cmihail): just for dev, delete when not needed any more
			System.err.println("Usage: ./Main <path_to_movie");
			System.exit(1);
		}

		final String pathToMovie = args[0];

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Player player = new Player(System.in, System.out);
				player.getModel().startMovie(pathToMovie);
			}
		});
	}
}
