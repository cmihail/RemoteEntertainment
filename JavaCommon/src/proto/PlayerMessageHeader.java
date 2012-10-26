package proto;

import proto.ProtoPlayer.MessageHeader;

/**
 * Defines the wrapper for the proto message header.
 *
 * @author cmihail (Mihail Costea)
 */
public class PlayerMessageHeader { // TODO: move in proto package

  private final MessageHeader.Type messageType;

  public PlayerMessageHeader(MessageHeader.Type messageType) {
    this.messageType = messageType;
  }

  // TODO(cmihail): getters

  public MessageHeader toProto() {
    return MessageHeader.newBuilder()
        .setMessageType(messageType)
        .build();
  }
}
