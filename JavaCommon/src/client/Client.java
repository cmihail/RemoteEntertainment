package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import logger.CommonLogger;
import proto.PlayerCommand;
import proto.PlayerMessageHeader;
import proto.ProtoPlayer.Command;
import proto.ProtoPlayer.MessageHeader;
import proto.ProtoPlayer.MessageHeader.Type;

import com.google.protobuf.CodedInputStream;

/**
 * Defines the client methods needed for connecting to the server and sending/receiving
 * player commands.
 *
 * @author cmihail (Mihail Costea)
 */
public class Client {

  private static final Logger logger = CommonLogger.getLogger("Client");

  private SocketChannel socketChannel = null;

  /**
   * Connects to server. The client connects only if there isn't already a successful connection.
   * @param ipAddress the ip address of the server
   * @param port the port that the server is using for listening
   */
  public void connect(String ipAddress, int port) {
    try {
      socketChannel = SocketChannel.open();
      socketChannel.socket().setReuseAddress(true);
      socketChannel.socket().connect(new InetSocketAddress(ipAddress, port));
      socketChannel.configureBlocking(true);
      logger.log(Level.INFO, "Connection was successful");
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
   * TODO(cmihail): comments + describe the protocol better in .proto file
   * @param buffer
   */
  private void writeToSocket(ByteArrayOutputStream out) {
    try {
      ByteBuffer buffer = ByteBuffer.allocate(4);
      buffer.order(ByteOrder.LITTLE_ENDIAN);
      buffer.putInt(out.size());
      buffer.flip();
      while (buffer.hasRemaining()) {
        socketChannel.write(buffer);
      }

      buffer = ByteBuffer.wrap(out.toByteArray());
      while (buffer.hasRemaining()) {
        socketChannel.write(buffer);
      }
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Sends a player command to the server as a proto command.
   * @param playerCommand the command that is sent
   */
  public void sendCommand(final PlayerCommand playerCommand) {
    Command command = playerCommand.toProto();
    MessageHeader messageHeader = new PlayerMessageHeader(Type.COMMAND).toProto();
    try {
      // Send message type preceded by its length.
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      messageHeader.writeDelimitedTo(out);
      writeToSocket(out);

      // Send command preceded by its length.
      out.reset();
      command.writeDelimitedTo(out);
      writeToSocket(out);
      logger.log(Level.INFO, "Command sent: " + command.getType().toString());
    } catch (IOException e) {
      exit(e);
    }
  }

  /**
   * Receives a command from the server as a proto command and converts it to a player command.
   * @return the command that was received
   * @throws AsynchronousCloseException if connection is lost when waiting for a command
   */
  public PlayerCommand receiveCommand() throws AsynchronousCloseException {
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
      throw e; // TODO(cmihail): maybe throw a exception with a more suggestive name
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
    e.printStackTrace();
//    e.printStackTrace(CommonLogger.getPrintStream()); // TODO(cmihail): doesn't print anything
  }
}
