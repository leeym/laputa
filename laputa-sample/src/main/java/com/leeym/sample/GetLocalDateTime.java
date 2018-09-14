package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

import java.time.LocalDateTime;

public class GetLocalDateTime extends Query<LocalDateTime> {

  @Inject
  LocalDateTime now;

  @Override
  public LocalDateTime process() {
    return now;
  }
}
