package com.leeym.platform.converters;

import com.google.common.base.Splitter;
import com.kaching.platform.converters.NullHandlingConverter;

import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesConverter extends NullHandlingConverter<Properties> {

  @Override
  protected String nonNullableToString(Properties value) {
    return "{"
      + value.entrySet().stream()
      .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
      .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(", "))
      + "}";
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
