package com.leeym.sample;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.Service;
import com.leeym.platform.lambda.Query;

import java.util.Set;

public class SampleService extends Service {

  @Override
  public Package getPackage() {
    return SampleService.class.getPackage();
  }

  @Override
  public Set<Class<? extends Query>> getQueries() {
    return ImmutableSet.of(
      GetHostAddress.class,
      GetHostName.class,
      GetLocalDate.class,
      GetLocalDateTime.class,
      GetProperties.class,
      GetZonedDateTime.class,
      Sort.class,
      Uniq.class
    );
  }

  @Override
  public InstantiatorModule getInstantiatorModule() {
    return new SampleInstantiatorModule();
  }

  @Override
  public Module getModule() {
    return new SampleModule();
  }
}
