package com.leeym.platform.common;

public class Tuple1<A> extends Tuple0 {

  private A a;

  public Tuple1(A a) {
    super();
    this.a = a;
  }

  public A getA() {
    return a;
  }

  public void setA(A a) {
    this.a = a;
  }
}
