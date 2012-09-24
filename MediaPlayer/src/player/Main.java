package player;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			// TODO(cmihail): just for dev, delete when not needed any more
			System.err.println("Usage: ./Player <path_to_movie> <port>");
			System.exit(1);
		}

		final String pathToMovie = args[0];

		// Connect to server.
		final Socket socket;
		InputStream inputStream = null;
		try {
		  InetAddress address = InetAddress.getByName("localhost");
		  socket = new Socket(address, Integer.parseInt(args[1]));
		  inputStream = socket.getInputStream();

		  System.out.println("[CLIENT] Connection was successful"); // TODO(cmihail): use logger
		} catch (UnknownHostException e) {
		  System.err.println("Wrong server address: " + e.getMessage());
		  System.exit(1);
		} catch (IOException e) {
		  System.err.println(e.getMessage());
		  System.exit(1);
		}

		final InputStream playerInputStream = inputStream;
		// Create media player.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Player player = new Player(playerInputStream, System.out);
				player.getModel().startMovie(pathToMovie);
			}
		});
	}
}
