package com.leeym.queries;

import com.leeym.platform.lambda.SimpleInstantiatorModule;
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

  @Test
  public void queryCanBeInstantiated() {
    Queries.getAllQueries()
      .forEach(aClass -> {
        try {
          createInstantiator(aClass, new SimpleInstantiatorModule());
        } catch (Throwable e) {
          throw new RuntimeException("Query [" + aClass.getSimpleName() + "] can not be instantiated", e);
        }
      });
  }

  @Test
  public void queriesAreTested() {
    Queries.getAllQueries()
      .forEach(aClass -> {
        String name = aClass.getName();
        try {
          Class.forName(name + "Test");
        } catch (ClassNotFoundException e) {
          fail("Query [" + aClass.getSimpleName() + "] is not tested");
        }
      });
  }

}
