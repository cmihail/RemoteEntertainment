package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;

import logger.CommonLogger;
import proto.ProtoPlayer;

import com.google.protobuf.CodedInputStream;

/**
 * Defines the client methods needed for connecting to the server and sending/receiving
 * player commands.
 *
 * @author cmihail (Mihail Costea)
 */
public class Client {

  public class ConnectionLostException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Connection with server was lost";

    public ConnectionLostException() {
      super(MESSAGE);
    }
  }

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
      CommonLogger.log(Level.INFO, "Connection was successful");
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }
  }

  /**
   * Disconnects from the server.
   */
  public void disconnect() {
    try {
      socketChannel.close(); // TODO(cmihail): maybe use shutdown on socket
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }
  }

  /**
   * Sends a message to the server. The message will be preceded by its length before sending it.
   * @param buffer the message to be sent as a byte array
   */
  public void send(byte[] buffer) {
    try {
      ByteBuffer byteBuffer = ByteBuffer.allocate(4 + buffer.length);
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN); // TODO(cmihail): think about this part
      byteBuffer.putInt(buffer.length);
      byteBuffer.put(buffer);
      byteBuffer.flip();
      while (byteBuffer.hasRemaining()) {
        socketChannel.write(byteBuffer);
      }
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }
  }

  /**
   * Reads a message from server. The message can be anything contained in the {@link ProtoPlayer}
   * class. Every message starts with a varint32 representing the number of bytes that
   * contain the {@link ProtoPlayer} class. Those bytes will be returned by this class.
   * Decoding the class should be done accordingly to the protocol.
   * @return a message from server as a byte array
   * @throws AsynchronousCloseException an exception thrown if the connection with the server
   * was lost when waiting to receive a message
   */
  public byte[] read() throws ConnectionLostException {
    try {
      CodedInputStream codedInputStream =
          CodedInputStream.newInstance(socketChannel.socket().getInputStream());
      int varint32 = codedInputStream.readInt32();
      return codedInputStream.readRawBytes(varint32);
    } catch (AsynchronousCloseException e) {
      CommonLogger.log(Level.WARNING, "Connection lost");
      throw new ConnectionLostException(); // TODO(cmihail): maybe throw a exception with a more suggestive name
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }

    return null;
  }
}
