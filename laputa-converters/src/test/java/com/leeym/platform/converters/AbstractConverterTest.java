package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractConverterTest<T> {

  public abstract T getValue();

  public abstract Converter<T> getConverter();

  @Test
  public void test() {
    T value = getValue();
    Converter<T> converter = getConverter();
    String representation = converter.toString(value);
    System.out.println(representation);
    assertEquals(value, converter.fromString(representation));
  }

}
