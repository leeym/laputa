package com.leeym.core;

import com.leeym.platform.lambda.Query;

public class Throw extends Query<String> {

  private String message;

  public Throw(String message) {
    this.message = message;
  }

  @Override
  public String process() {
    throw new RuntimeException(message);
  }
}
