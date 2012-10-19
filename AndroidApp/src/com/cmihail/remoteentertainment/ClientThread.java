package com.cmihail.remoteentertainment;

import client.Client;
import client.PlayerCommand;
import client.PlayerCommandExecutor;

/**
 * TODO(cmihail): only for dev
 *
 * @author cmihail (Mihail Costea)
 */
public class ClientThread extends Thread {

  private final PlayerCommandExecutor commandExecutor;

  private Client client;

  public ClientThread(PlayerCommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
    client = new Client();

  }

  @Override
  public void run() {
    // Create a client to connect to server. // TODO(cmihail): set params into settings activity
    client.connect("192.168.2.12", 10000);

    while (true) {
      PlayerCommand command = null;
      command = client.receiveCommand();
      if (command == null) {
        break;
      }

      commandExecutor.executeCommand(command, false);
    }
  }

  public void exit() {
    client.disconnect();
  }

  // TODO(cmihail): sendCommand should be ran into a different thread,
  // think about doing this with all the client class
//  public void sendCommand(final Type type) {
//    Thread thread = new Thread() { // TODO(cmihail): use only one thread, not multiple
//      @Override
//      public void run() {
//        client.sendCommand(new PlayerCommand(type));
//      }
//    };
//    thread.start();
//  }

  public Client getClient() {
    return client;
  }
}
