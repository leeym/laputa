package com.leeym.platform.converters;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaching.platform.converters.NullHandlingConverter;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class GsonConverter<T> extends NullHandlingConverter<T> {

  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
  };
  private final Type type = typeToken.getType();

  @Override
  protected T fromNonNullableString(String representation) {
    return gson.fromJson(representation, type);
  }

  @Override
  protected String nonNullableToString(T value) {
    return gson.toJson(sort(new JsonParser().parse(gson.toJson(value))));
  }

  private static JsonElement sort(JsonElement jsonElement) {
    if (jsonElement.isJsonArray()) {
      jsonElement.getAsJsonArray().forEach(GsonConverter::sort);
    } else if (jsonElement.isJsonObject()) {
      Map<String, JsonElement> map = new TreeMap<>();
      jsonElement.getAsJsonObject().entrySet().forEach(entry -> map.put(entry.getKey(), entry.getValue()));
      map.forEach((key, value) -> {
        jsonElement.getAsJsonObject().remove(key);
        jsonElement.getAsJsonObject().add(key, sort(value));
      });
    }
    return jsonElement;
  }
}
