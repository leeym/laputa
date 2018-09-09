package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDualConverterTest<T, V> extends AbstractConverterTest<T> {

  public abstract V getValue2();

  @Test
  public void test2() {
    Converter<T> converter = getConverter();
    assertEquals(getValue2().toString(), converter.toString(getValue()));
  }

}
