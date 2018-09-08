package com.leeym.queries;

import java.util.Properties;

public class GetProperties extends AbstractQuery<Properties> {

  @Override
  public Properties process() {
    return System.getProperties();
  }
}
