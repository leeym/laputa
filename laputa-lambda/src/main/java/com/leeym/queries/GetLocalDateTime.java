package com.leeym.queries;

import com.google.inject.Inject;

import java.time.LocalDateTime;

public class GetLocalDateTime extends AbstractQuery<LocalDateTime> {

  @Inject
  LocalDateTime now;

  @Override
  public LocalDateTime process() {
    return now;
  }
}
