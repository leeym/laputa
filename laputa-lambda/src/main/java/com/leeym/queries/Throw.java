package com.leeym.queries;

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
