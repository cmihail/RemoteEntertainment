package com.cmihail.remoteentertainment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

import proto.ProtoBuilder;
import proto.ProtoPlayer.Command.Type;

public class ClientThread extends Thread {

  public interface ServerCallback { // TODO(cmihail): unify with PlayerModel Handler
    void onRewind();
    void onFastForward();
    void onPlay();
    void onPause();
  }

  private final ServerCallback serverCallback;
  private SocketChannel socketChannel;

  public ClientThread() {
    serverCallback = createServerCallback();
  }

  public ServerCallback getServerCallback() {
    return serverCallback;
  }

  @Override
  public void run() {
    // Connect to server.
    try {
      socketChannel = SocketChannel.open();
      socketChannel.socket().setReuseAddress(true);
      // TODO(cmihail): set params into settings activity
      socketChannel.socket().connect(new InetSocketAddress("192.168.2.2", 10000));
      socketChannel.configureBlocking(true);
    } catch (IOException e) {
      System.out.println(e.getMessage()); // TODO(cmihail): use logger
      System.exit(1);
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
          ProtoBuilder.sendCommand(ProtoBuilder.createCommand(type), socketChannel);
        }
      };
      thread.start();
    }
}
