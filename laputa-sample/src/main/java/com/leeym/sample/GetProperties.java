package com.leeym.sample;

import com.leeym.platform.lambda.AbstractQuery;

import java.util.Properties;

public class GetProperties extends AbstractQuery<Properties> {

  @Override
  public Properties process() {
    return System.getProperties();
  }
}
