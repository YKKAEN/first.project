package my.first.project.services;

import my.first.project.entities.City;
import my.first.project.entities.Product;

import java.util.List;

public interface CityService {
    List<City> getAllCities();

    void saveCity(City city);

    City getCityById(Long id);

    void deleteCityById(Long id);

    City createCity(City city);
}
