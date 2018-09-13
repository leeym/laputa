package com.leeym.core;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class SimpleLogger implements LambdaLogger {

  @Override
  public void log(String message) {
    System.out.println(message);
  }

  @Override
  public void log(byte[] message) {
    log(new String(message));
  }
}
