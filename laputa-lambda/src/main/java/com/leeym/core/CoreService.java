package com.leeym.core;

import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.AbstractService;

public class CoreService extends AbstractService {

  @Override
  public InstantiatorModule getInstantiatorModule() {
    return binder -> {
    };
  }

  @Override
  public Module getModule() {
    return binder -> {
    };
  }
}
