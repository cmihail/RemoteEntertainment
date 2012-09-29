package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.google.protobuf.CodedInputStream;

import proto.ProtoPlayer.Command;

/**
 * Defines the client methods needed for connecting to the server and sending/receiving
 * player commands.
 *
 * @author cmihail (Mihail Costea)
 */
public class Client {

  private SocketChannel socketChannel = null;

  public Client() {
    try {
      socketChannel = SocketChannel.open();
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Connects to server. The client connects only if there isn't already a succesuful connection.
   * @param ipAddress the ip address of the server
   * @param port the port that the server is using for listening
   */
  public void connect(String ipAddress, int port) {
    try {
      if (!socketChannel.socket().isConnected()) {
        socketChannel.socket().setReuseAddress(true);
        socketChannel.socket().connect(new InetSocketAddress(ipAddress, port));
        socketChannel.configureBlocking(true);
      }
    } catch (IOException e) {
      exit(e);
    }

    System.out.println("[Client] Connection was successful"); // TODO(cmihail): use logger
  }

  /**
   * Sends a player command to the server as a proto command.
   * @param playerCommand the command that is sent
   */
  public void sendCommand(PlayerCommand playerCommand) {
    Command command = playerCommand.toProto();
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      command.writeDelimitedTo(out);

      ByteBuffer commandBuffer = ByteBuffer.wrap(out.toByteArray());
      while (commandBuffer.hasRemaining()) {
        socketChannel.write(commandBuffer);
      }
      System.out.println("[Client] Command sent: " +
          command.getType().toString()); // TODO(cmihail): use logger
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Receives a command from the server as a proto command and converts it to a player command.
   * @return the command that was received.
   */
  public PlayerCommand receiveCommand() {
    Command command = null;
    try {
      CodedInputStream codedInputStream =
          CodedInputStream.newInstance(socketChannel.socket().getInputStream());
      int varint32 = codedInputStream.readInt32();
      byte[] bytes = codedInputStream.readRawBytes(varint32);
      command = Command.parseFrom(bytes);
      System.out.println("[Client] Command recv: " +
          command.getType().toString()); // TODO(cmihail): use logger
    } catch (IOException e) {
      exit(e);
    }

    if (command.hasInfo()) {
      return new PlayerCommand(command.getType(), command.getInfo().getValue());
    }
    return new PlayerCommand(command.getType());
  }

  /**
   * Terminates the client when receives an exception.
   */
  private void exit(Exception e) {
    e.printStackTrace(); // TODO(cmihail): use logger
    System.out.println(e.getMessage());
    System.exit(1);
  }
}
