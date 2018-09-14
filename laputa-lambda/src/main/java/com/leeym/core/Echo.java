package com.leeym.core;

import com.leeym.platform.lambda.Query;

public class Echo extends Query<String> {

  private final String message;

  public Echo(String message) {
    this.message = message;
  }

  @Override
  public String process() {
    return message;
  }
}
