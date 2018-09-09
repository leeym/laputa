package com.leeym.platform.converters;

import com.kaching.platform.converters.NullHandlingConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends NullHandlingConverter<LocalDateTime> {

  @Override
  protected LocalDateTime fromNonNullableString(String representation) {
    return LocalDateTime.parse(representation);
  }

  @Override
  protected String nonNullableToString(LocalDateTime value) {
    return value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
