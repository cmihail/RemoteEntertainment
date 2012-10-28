package proto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import logger.CommonLogger;
import proto.ProtoPlayer.Command;

/**
 * Defines the wrapper for the proto command.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerCommand {

  private final Command.Type type;
  private final String info;

  /**
   * @param type the type of the command
   */
  public PlayerCommand(Command.Type type) {
    this(type, null);
  }

  /**
   * @param type the type of the command
   * @param info information about the command
   */
  public PlayerCommand(Command.Type type, String info) {
    this.type = type;
    this.info = info;
  }

  /**
   * @param bytes the bytes that contain a {@link proto.Command}
   */
  public PlayerCommand(byte[] bytes) {
    Command command = null;
    try {
      command = Command.parseFrom(bytes);
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }

    type = command.getType();
    if (command.hasInfo()) {
      info = command.getInfo().getValue();
    } else {
      info = null;
    }
  }

  /**
   * @return the type of the command
   */
  public Command.Type getType() {
    return type;
  }

  /**
   * @return the information about the command, or null if none
   */
  public String getInfo() {
    return info;
  }

  /**
   * @return the command as a proto
   */
  public Command toProto() {
    Command.Builder builder = Command.newBuilder().setType(type);
    if (info != null) {
      builder.setInfo(Command.Information.newBuilder().setValue(info).build());
    }
    return builder.build();
  }

  /**
   * @return the command as a byte array
   */
  public byte[] toByteArray() {
    Command command = this.toProto();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      command.writeDelimitedTo(out);
    } catch (IOException e) {
      CommonLogger.logException(Level.SEVERE, e);
    }
    return out.toByteArray();
  }
}
