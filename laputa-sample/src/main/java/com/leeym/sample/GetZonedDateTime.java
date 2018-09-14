package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

import java.time.ZonedDateTime;

public class GetZonedDateTime extends Query<ZonedDateTime> {

  @Inject
  ZonedDateTime zonedDateTime;

  @Override
  public ZonedDateTime process() {
    return zonedDateTime;
  }
}
