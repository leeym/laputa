package com.leeym.core;

import com.leeym.platform.lambda.AbstractQuery;

public class Echo extends AbstractQuery<String> {

  private final String message;

  public Echo(String message) {
    this.message = message;
  }

  @Override
  public String process() {
    return message;
  }
}
