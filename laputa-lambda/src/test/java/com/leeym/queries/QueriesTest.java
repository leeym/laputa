package com.leeym.queries;

import com.kaching.platform.converters.Instantiators;
import com.leeym.platform.queryengine.SimpleInstantiatorModule;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QueriesTest {

  @Test
  public void queriesArePublic() {
    Queries.getAllQueries().forEach(aClass -> assertTrue(aClass.getName(), Modifier.isPublic(aClass.getModifiers())));
  }

  @Test
  public void queriesHaveOnlyOneConstructor() {
    Queries.getAllQueries().forEach(aClass -> assertEquals(aClass.getName(), 1, aClass.getConstructors().length));
  }

  @Test
  public void queryConstructorsArePublic() {
    Queries.getAllQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getConstructors()))
      .forEach(constructor -> assertTrue(Modifier.isPublic(constructor.getModifiers())));
  }

  @Ignore
  @Test
  public void queryCanBeInstantiated() {
    Queries.getAllQueries()
      .forEach(aClass -> {
        try {
          createInstantiator(aClass, new SimpleInstantiatorModule());
        } catch (Throwable e) {
          fail(aClass.getName());
        }
      });
  }

}