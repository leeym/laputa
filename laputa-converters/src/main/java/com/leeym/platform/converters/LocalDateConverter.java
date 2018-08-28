package com.leeym.platform.converters;

import com.kaching.platform.converters.NullHandlingConverter;

import java.time.LocalDate;

public class LocalDateConverter extends NullHandlingConverter<LocalDate> {

  @Override
  protected LocalDate fromNonNullableString(final String s) {
    return LocalDate.parse(s);
  }

  @Override
  protected String nonNullableToString(final LocalDate localDate) {
    return localDate.toString();
  }
}
