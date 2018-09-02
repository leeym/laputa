package com.leeym.queries;

public class Hello extends AbstractQuery<String> {

  private final String input;

  public Hello(String input) {
    this.input = input;
  }

  @Override
  public String process() {
    return "Hello, " + input + ".";
  }
}
