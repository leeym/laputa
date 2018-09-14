package com.leeym.sample;

import com.leeym.platform.lambda.Query;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHostAddress extends Query<String> {

  @Override
  public String process() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}
