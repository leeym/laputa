package com.leeym.sample;

import com.leeym.platform.lambda.AbstractQuery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHostName extends AbstractQuery<String> {

  @Override
  public String process() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}
