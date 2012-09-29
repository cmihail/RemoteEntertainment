package client;

import proto.ProtoPlayer.Command;
import proto.ProtoPlayer.Command.Information;
import proto.ProtoPlayer.Command.Type;

/**
 * Defines the wrapper for the proto command.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerCommand {

  private final Type type;
  private final String info;

  /**
   * @param type the type of the command
   */
  public PlayerCommand(Type type) {
    this(type, null);
  }

  /**
   * @param type the type of the command
   * @param info information about the command
   */
  public PlayerCommand(Type type, String info) {
    this.type = type;
    this.info = info;
  }

  /**
   * @return the type of the command
   */
  public Type getType() {
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
      builder.setInfo(Information.newBuilder().setValue(info).build());
    }
    return builder.build();
  }
}
