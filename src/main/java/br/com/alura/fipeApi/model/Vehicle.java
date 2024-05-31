package br.com.alura.fipeApi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(
  @JsonAlias("Valor") String price,
  @JsonAlias("Marca") String brand,
  @JsonAlias("Modelo") String model,
  @JsonAlias("AnoModelo") Integer modelYear,
  @JsonAlias("Combustivel") String fuel
) {
}
