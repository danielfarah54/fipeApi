package br.com.alura.fipeApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConvertData implements IConvertData {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T getData(String json, Class<T> tClass) {
    try {
      return mapper.readValue(json, tClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> List<T> getList(String json, Class<T> tClass) {
    CollectionType listType = mapper.getTypeFactory()
      .constructCollectionType(List.class, tClass);

    try {
      return mapper.readValue(json, listType);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
