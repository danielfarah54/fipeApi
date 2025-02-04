package br.com.alura.fipeApi.service;

import java.util.List;

public interface IConvertData {
  <T> T getData(String json, Class<T> tClass);

  <T> List<T> getList(String json, Class<T> tClass);
}
