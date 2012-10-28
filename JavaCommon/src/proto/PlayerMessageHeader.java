package proto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import logger.CommonLogger;
import proto.ProtoPlayer.MessageHeader;

/**
 * Defines the wrapper for the proto message header.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerMessageHeader {

  private final MessageHeader.Type type;

  /**
   * @param type the type of the message contained in the header
   */
  public PlayerMessageHeader(MessageHeader.Type type) {
    this.type = type;
  }

  public PlayerMessageHeader(byte[] bytes) {
    MessageHeader header = null;
    try {
      header = MessageHeader.parseFrom(bytes);
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }

    type = header.getType();
  }

  /**
   * @return the type of the message contained in the header
   */
  public MessageHeader.Type getType() {
    return type;
  }

  /**
   * @return the header as a proto
   */
  public MessageHeader toProto() {
    return MessageHeader.newBuilder()
        .setType(type)
        .build();
  }

  /**
   * @return the command as a byte array
   */
  public byte[] toByteArray() {
    MessageHeader header = this.toProto();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      header.writeDelimitedTo(out);
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }
    return out.toByteArray();
  }
}
