package com.leeym.sample;

import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

import java.time.LocalDate;

public class GetLocalDate extends Query<LocalDate> {

  @Inject
  LocalDate today;

  @Override
  public LocalDate process() {
    return today;
  }
}
