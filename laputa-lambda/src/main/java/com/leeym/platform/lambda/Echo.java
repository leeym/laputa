package com.leeym.platform.lambda;

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
