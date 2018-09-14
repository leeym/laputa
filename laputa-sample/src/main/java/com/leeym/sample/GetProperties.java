package com.leeym.sample;

import com.leeym.platform.lambda.Query;

import java.util.Properties;

public class GetProperties extends Query<Properties> {

  @Override
  public Properties process() {
    return System.getProperties();
  }
}
