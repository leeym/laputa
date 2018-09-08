package com.leeym.queries;

import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.SimpleInstantiatorModule;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static com.kaching.platform.converters.Instantiators.createConverter;
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
    InstantiatorModule module = new SimpleInstantiatorModule();
    Queries.getAllQueries()
      .forEach(aClass -> {
        try {
          createInstantiator(aClass, module);
        } catch (Exception e) {
          throw new RuntimeException("Query [" + aClass.getSimpleName() + "] can not be instantiated", e);
        }
        Arrays.stream(aClass.getConstructors())
          .forEach(constructor -> Arrays.stream(constructor.getParameterTypes()).forEach(parameterType -> {
            try {
              createConverter(parameterType, module);
            } catch (Exception e) {
              throw new RuntimeException("Parameter [" + parameterType.getSimpleName() + "] can not be converted", e);
            }
          }));
        Class<?> returnType;
        try {
          returnType = aClass.getDeclaredMethod(Query.METHOD_NAME).getReturnType();
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
        try {
          createConverter(returnType, module);
        } catch (Exception e) {
          throw new RuntimeException("Return type [" + returnType.getSimpleName() + "] can not be converted", e);
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
