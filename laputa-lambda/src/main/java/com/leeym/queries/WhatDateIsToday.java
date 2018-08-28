package com.leeym.queries;

import com.leeym.platform.queryengine.QueryExecutorService;

import javax.inject.Inject;

public class WhatDateIsToday extends AbstractQuery<String> {

  @Inject
  QueryExecutorService service;

  @Override
  public String process() {
    return "Today is " + service.submitAndGetResult(new GetToday());
  }
}
