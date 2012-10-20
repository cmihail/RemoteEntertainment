package com.cmihail.remoteentertainment;

import java.nio.channels.AsynchronousCloseException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.Client;
import client.PlayerCommand;

public class AndroidClient extends Client {

  private final Lock clientLock = new ReentrantLock();
  private boolean isConnected = false;

  @Override
  public void connect(final String ipAddress, final int port) {
    clientLock.lock();
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        clientLock.lock();
        try {
          AndroidClient.super.connect(ipAddress, port);
          isConnected = true;
        } finally {
          clientLock.unlock();
        }
      }
    });
    try {
      thread.start();
    } finally {
      clientLock.unlock();
    }
  }

  @Override
  public void sendCommand(final PlayerCommand playerCommand) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        AndroidClient.super.sendCommand(playerCommand);
      }
    });
    thread.start();
  }

  @Override
  public PlayerCommand receiveCommand() throws AsynchronousCloseException {
    // Awaits for client to connect to the server.
    while (!isConnected) { // TODO(cmihail): after some time automatic break and show error
      clientLock.lock();
      clientLock.unlock();
    }
    return super.receiveCommand();
  }
}
