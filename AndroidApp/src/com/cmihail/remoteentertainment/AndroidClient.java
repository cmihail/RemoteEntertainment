package com.cmihail.remoteentertainment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.Client;

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
  public void send(final byte[] buffer) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        AndroidClient.super.send(buffer);
      }
    });
    thread.start();
  }

  @Override
  public byte[] read() throws Client.ConnectionLostException {
    // Awaits for client to connect to the server.
    while (!isConnected) { // TODO(cmihail): after some time automatic break and show error
      clientLock.lock();
      clientLock.unlock();
    }
    return super.read();
  }
}
