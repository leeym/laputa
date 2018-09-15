package com.leeym.platform.common.tuple;

public class Tuple2<A, B> extends Tuple1<A> {

  public B b;

  public Tuple2(A a, B b) {
    super(a);
    this.b = b;
  }

  public B getB() {
    return b;
  }

  public void setB(B b) {
    this.b = b;
  }
}
