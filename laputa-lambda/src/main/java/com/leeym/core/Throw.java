package com.leeym.core;

import com.leeym.platform.lambda.AbstractQuery;

public class Throw extends AbstractQuery<String> {

  private String message;

  public Throw(String message) {
    this.message = message;
  }

  @Override
  public String process() {
    throw new RuntimeException(message);
  }
}
