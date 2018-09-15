package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

import java.util.Properties;

public class PropertiesConverterTest extends AbstractConverterTest<Properties> {

  @Override
  public Properties getValue() {
    return System.getProperties();
  }

  @Override
  public Converter<Properties> getConverter() {
    return new PropertiesConverter();
  }

}
