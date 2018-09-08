package com.leeym.platform.converters;

import com.google.common.base.Splitter;
import com.kaching.platform.converters.NullHandlingConverter;

import java.util.Properties;

public class PropertiesConverter extends NullHandlingConverter<Properties> {

  @Override
  protected String nonNullableToString(Properties value) {
    return value.toString();
  }

  @Override
  protected Properties fromNonNullableString(String representation) {
    Properties properties = new Properties();
     Splitter.on(", ")
      .withKeyValueSeparator("=")
      .split(representation.substring(1, representation.length() - 1)).forEach(properties::setProperty);
     return properties;
  }
}
