package player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

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
		SocketChannel socketChannel = null;
		try {
	    socketChannel = SocketChannel.open();
	    socketChannel.socket().setReuseAddress(true);
	    socketChannel.socket().connect(
	        new InetSocketAddress("localhost", Integer.parseInt(args[1])));
	    socketChannel.configureBlocking(true);
    } catch (IOException e) {
      System.out.println(e.getMessage()); // TODO(cmihail): use logger
      System.exit(1);
    }

		System.out.println("[CLIENT] Connection was successful"); // TODO(cmihail): use logger

		// Create media player.
		final SocketChannel finalSocketChannel = socketChannel;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			  try {
			    Player player = new Player(finalSocketChannel, finalSocketChannel.socket().getInputStream(),
			        finalSocketChannel.socket().getOutputStream());
			    player.getModel().startMovie(pathToMovie);
			  } catch (IOException e) {
		      System.out.println(e.getMessage()); // TODO(cmihail): use logger
		      System.exit(1);
			  }
			}
		});
	}
}
