package com.leeym.platform.common.tuple;

public class Tuple7<A, B, C, D, E, F, G> extends Tuple6<A, B, C, D, E, F> {

  private G g;

  public Tuple7(A a, B b, C c, D d, E e, F f, G g) {
    super(a, b, c, d, e, f);
    this.g = g;
  }

  public G getG() {
    return g;
  }

  public void setG(G g) {
    this.g = g;
  }
}
