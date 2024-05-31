package br.com.alura.fipeApi.main;

import br.com.alura.fipeApi.model.Data;
import br.com.alura.fipeApi.model.Vehicle;
import br.com.alura.fipeApi.model.VehicleModel;
import br.com.alura.fipeApi.service.ConvertData;
import br.com.alura.fipeApi.service.FetchApi;

import java.util.*;

public class Main {
  private final Scanner scanner = new Scanner(System.in);
  private final FetchApi fetchApi = new FetchApi();
  private final ConvertData convertData = new ConvertData();

  public void showMenu() {
    var menu = """
      Options:
        Cars
        Motorcycles
        Trucks
      """;
    System.out.println("\n" + menu);
    System.out.println("Choose an option: ");

    var option = scanner.nextLine();

    String address = "https://parallelum.com.br/fipe/api/v1/";
    if (option.toLowerCase().contains("car")) {
      address += "carros/marcas/";
    } else if (option.toLowerCase().contains("mot")) {
      address += "motos/marcas/";
    } else if (option.toLowerCase().contains("tru")) {
      address += "caminhoes/marcas/";
    } else {
      System.out.println("Invalid option");
      System.exit(1);
    }

    String json = fetchApi.getData(address);

    var brandList = convertData.getList(json, Data.class);

    brandList
      .stream()
      .sorted(Comparator.comparing(Data::name))
      .forEach(System.out::println);

    System.out.println("\nChoose the brand code: ");
    var brandCode = scanner.nextLine();

    address += brandCode + "/modelos/";

    json = fetchApi.getData(address);

    var modelList = convertData.getData(json, VehicleModel.class);

    modelList.models()
      .stream()
      .sorted(Comparator.comparing(Data::name))
      .forEach(System.out::println);

    System.out.println("\nEnter a part of the model name: ");

    var modelName = scanner.nextLine();
    List<Data> filteredModels = modelList.models()
      .stream()
      .filter(model -> model.name().toLowerCase().contains(modelName.toLowerCase()))
      .sorted(Comparator.comparing(Data::name))
      .toList();

    System.out.println("\nModels: ");
    filteredModels.forEach(System.out::println);

    System.out.println("\nChoose the model code: ");
    var modelCode = scanner.nextLine();
    address += modelCode + "/anos/";

    json = fetchApi.getData(address);
    List<Data> yearList = convertData.getList(json, Data.class);
    List<Vehicle> vehicles = new ArrayList<>();

    for (Data year : yearList) {
      json = fetchApi.getData(address + year.code());
      Vehicle vehicle = convertData.getData(json, Vehicle.class);
      vehicles.add(vehicle);
    }

    System.out.println("\nVehicles: ");
    vehicles.forEach(System.out::println);
  }
}
