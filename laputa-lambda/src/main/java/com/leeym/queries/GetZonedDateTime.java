package com.leeym.queries;

import javax.inject.Inject;
import java.time.ZonedDateTime;

public class GetZonedDateTime extends AbstractQuery<ZonedDateTime> {

  @Inject ZonedDateTime zonedDateTime;

  @Override
  public ZonedDateTime process() {
    return zonedDateTime;
  }
}
