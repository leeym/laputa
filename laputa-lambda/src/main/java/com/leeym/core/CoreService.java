package com.leeym.core;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.common.sleeper.DefaultSleeper;
import com.leeym.platform.common.sleeper.Sleeper;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Service;

import java.util.Set;

public class CoreService extends Service {

  @Override
  public Set<Class<? extends Query>> getQueries() {
    return ImmutableSet.of(
      Echo.class,
      GetContext.class,
      GetRequest.class,
      Help.class,
      Sleep.class,
      Throw.class
    );
  }

  @Override
  public Package getPackage() {
    return CoreService.class.getPackage();
  }

  @Override
  public InstantiatorModule getInstantiatorModule() {
    return binder -> {
    };
  }

  @Override
  public Module getModule() {
    return binder -> {
      binder.bind(Sleeper.class).toInstance(new DefaultSleeper());
    };
  }
}
