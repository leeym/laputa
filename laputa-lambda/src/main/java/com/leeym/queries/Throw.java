package com.leeym.queries;

import com.kaching.platform.converters.Optional;

public class Throw extends AbstractQuery<String> {

  private String message;

  public Throw(@Optional String message) {
    this.message = message;
  }

  @Override
  public String process() {
    throw new RuntimeException(message);
  }
}
