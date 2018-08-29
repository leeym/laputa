package com.leeym.queries;

import com.google.inject.Inject;
import com.leeym.platform.queryengine.QueryExecutorService;

public class WhatDateIsToday extends AbstractQuery<String> {

  @Inject
  QueryExecutorService service;

  @Override
  public String process() {
    return "Today is " + service.submitAndGetResult(new GetToday());
  }
}
