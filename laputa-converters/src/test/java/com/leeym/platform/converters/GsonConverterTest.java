package com.leeym.platform.converters;

public class GsonConverterTest extends AbstractConverterTest<GsonConverterTest.TestClass> {

  static class TestClass {
    String key;
    String value;

    TestClass(String key, String value) {
      this.key = key;
      this.value = value;
    }
  }

  @Override
  public TestClass getValue() {
    return new TestClass("foo", "bar");
  }

  @Override
  public GsonConverter<TestClass> getConverter() {
    return new GsonConverter<>();
  }

}
