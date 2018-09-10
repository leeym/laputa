package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.AbstractQuery;

import java.time.LocalDate;

public class GetLocalDate extends AbstractQuery<LocalDate> {

  @Inject
  LocalDate today;

  @Override
  public LocalDate process() {
    return today;
  }
}
