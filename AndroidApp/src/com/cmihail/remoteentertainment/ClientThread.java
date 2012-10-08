package com.cmihail.remoteentertainment;

import client.Client;
import client.PlayerCommand;
import proto.ProtoPlayer.Command.Type;

/**
 * TODO(cmihail): comments
 *
 * @author cmihail (Mihail Costea)
 */
public class ClientThread extends Thread {

  private Client client;

  public ClientThread() {
    client = new Client();
  }

  @Override
  public void run() {
    // Create a client to connect to server. // TODO(cmihail): set params into settings activity
    client.connect("192.168.2.2", 10000);
    // TODO(cmihail): problem, client reconnects when changing layout vertically / horizontally
    // and doesn't close old socket either

    while (true) {
      PlayerCommand playerCommmand = client.receiveCommand();

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

  public void sendCommand(final Type type) {
    Thread thread = new Thread() { // TODO(cmihail): use only one thread, not multiple
      @Override
      public void run() {
        client.sendCommand(new PlayerCommand(type));
      }
    };
    thread.start();
  }
}
