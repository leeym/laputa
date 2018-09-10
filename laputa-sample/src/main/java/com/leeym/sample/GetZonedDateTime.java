package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.AbstractQuery;

import java.time.ZonedDateTime;

public class GetZonedDateTime extends AbstractQuery<ZonedDateTime> {

  @Inject
  ZonedDateTime zonedDateTime;

  @Override
  public ZonedDateTime process() {
    return zonedDateTime;
  }
}
