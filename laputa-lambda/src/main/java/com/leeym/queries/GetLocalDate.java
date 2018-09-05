package com.leeym.queries;

import com.google.inject.Inject;

import java.time.LocalDate;

public class GetLocalDate extends AbstractQuery<LocalDate> {

  @Inject
  LocalDate today;

  @Override
  public LocalDate process() {
    return today;
  }
}
