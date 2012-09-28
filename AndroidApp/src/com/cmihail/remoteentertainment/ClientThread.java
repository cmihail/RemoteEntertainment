package com.cmihail.remoteentertainment;

import proto.Client;
import proto.PlayerCommand;
import proto.ProtoPlayer.Command.Type;

public class ClientThread extends Thread {

  public interface ServerCallback { // TODO(cmihail): unify with PlayerModel Handler
    void onRewind();
    void onFastForward();
    void onPlay();
    void onPause();
  }

  private final ServerCallback serverCallback;
  private Client client;

  public ClientThread() {
    serverCallback = createServerCallback();
  }

  public ServerCallback getServerCallback() {
    return serverCallback;
  }

  @Override
  public void run() {
    // Create a client to connect to server. // TODO(cmihail): set params into settings activity
    client = new Client("192.168.2.2", 10000);

    while (true) {
      PlayerCommand playerCommmand = client.recvCommand();

      // Execute command. TODO(cmihail): elaborate
      if (playerCommmand.getType() == Type.PLAY) {
        System.out.println("[Android] Play");
      } else if (playerCommmand.getType() == Type.PAUSE) {
        System.out.println("[Android] Pause");
      } else {
        System.out.println("[Player]: Wrong command!!!"); // TODO(cmihail): use logger
      }
    }
  }

  private ServerCallback createServerCallback() {
    return new ServerCallback() {

      @Override
      public void onRewind() {
        sendCommand(Type.REWIND);
      }

      @Override
      public void onPlay() {
        sendCommand(Type.PLAY);
      }

      @Override
      public void onPause() {
        sendCommand(Type.PAUSE);
      }

      @Override
      public void onFastForward() {
        sendCommand(Type.FAST_FORWARD);
      }
    };
  }

    private void sendCommand(final Type type) {
      Thread thread = new Thread() {
        @Override
        public void run() {
          client.sendCommand(new PlayerCommand(type));
        }
      };
      thread.start();
    }
}
