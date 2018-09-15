package com.leeym.platform.common.tuple;

public class Tuple8<A, B, C, D, E, F, G, H> extends Tuple7<A, B, C, D, E, F, G> {

  private H h;

  public Tuple8(A a, B b, C c, D d, E e, F f, G g, H h) {
    super(a, b, c, d, e, f, g);
    this.h = h;
  }

  public H getH() {
    return h;
  }

  public void setH(H h) {
    this.h = h;
  }
}
