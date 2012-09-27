package proto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.google.protobuf.CodedOutputStream;

import proto.ProtoPlayer.Command;
import proto.ProtoPlayer.Command.Information;

public class ProtoBuilder {

  public static Command createCommand(Command.Type type) {
    return Command.newBuilder().setType(type).build();
  }

  public static Command createCommand(Command.Type type, String info) {
    return Command.newBuilder()
        .setType(type)
        .setInfo(Information.newBuilder().setValue(info).build())
        .build();
  }

  public static void sendCommand(Command command, SocketChannel socketChannel) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      command.writeDelimitedTo(out);

      ByteBuffer commandBuffer = ByteBuffer.wrap(out.toByteArray());
      System.out.println("[Android] Size: " + commandBuffer.remaining());
      while (commandBuffer.hasRemaining()) {
        socketChannel.write(commandBuffer);
      }
      System.out.println("[Android] Command sent: " + command.getType().toString());
    } catch (IOException e) {
      System.out.println("[Android] Command error"); // TODO(cmihail): use logger
      e.printStackTrace();
    }
  }
}
