package com.leeym.platform.converters;

import com.kaching.platform.converters.NullHandlingConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter extends NullHandlingConverter<ZonedDateTime> {

  @Override
  protected ZonedDateTime fromNonNullableString(String representation) {
    return ZonedDateTime.parse(representation);
  }

  @Override
  protected String nonNullableToString(ZonedDateTime value) {
    return value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
