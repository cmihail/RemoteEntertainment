package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import logger.CommonLogger;

import com.google.protobuf.CodedInputStream;

import proto.ProtoPlayer.Command;

/**
 * Defines the client methods needed for connecting to the server and sending/receiving
 * player commands.
 *
 * @author cmihail (Mihail Costea)
 */
public class Client {

  private final Logger logger = CommonLogger.getLogger("Client");

  private SocketChannel socketChannel = null;

  public Client() {
    try {
      socketChannel = SocketChannel.open();
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Connects to server. The client connects only if there isn't already a successful connection.
   * @param ipAddress the ip address of the server
   * @param port the port that the server is using for listening
   */
  public void connect(String ipAddress, int port) {
    // TODO(cmihail): see if this should be or not a part of constructor
    try {
      if (!socketChannel.socket().isConnected()) {
        socketChannel.socket().setReuseAddress(true);
        socketChannel.socket().connect(new InetSocketAddress(ipAddress, port));
        socketChannel.configureBlocking(true);
        logger.log(Level.INFO, "Connection was successful");
      }
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Disconnects from the server.
   */
  public void disconnect() {
    try {
      socketChannel.close(); // TODO(cmihail): maybe use shutdown on socket
    } catch (IOException e) {
      exit(e);
    }
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
      logger.log(Level.INFO, "Command sent: " + command.getType().toString());
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Receives a command from the server as a proto command and converts it to a player command.
   * @return the command that was received, or null in case of connection lost
   * TODO(cmihail): a more elegant way to deal with the asynchronous close exception
   */
  public PlayerCommand receiveCommand() {
    Command command = null;
    try {
      CodedInputStream codedInputStream =
          CodedInputStream.newInstance(socketChannel.socket().getInputStream());
      int varint32 = codedInputStream.readInt32();
      byte[] bytes = codedInputStream.readRawBytes(varint32);
      command = Command.parseFrom(bytes);
      logger.log(Level.INFO, "Command received: " + command.getType().toString());
    } catch (AsynchronousCloseException e) {
      logger.log(Level.WARNING, "Connection lost");
      return null;
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
    logger.log(Level.SEVERE, e.getMessage());
    e.printStackTrace(CommonLogger.getPrintStream()); // TODO(cmihail): doesn't print anything
  }
}
