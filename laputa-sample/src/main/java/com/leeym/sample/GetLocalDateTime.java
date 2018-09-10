package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.AbstractQuery;

import java.time.LocalDateTime;

public class GetLocalDateTime extends AbstractQuery<LocalDateTime> {

  @Inject
  LocalDateTime now;

  @Override
  public LocalDateTime process() {
    return now;
  }
}
