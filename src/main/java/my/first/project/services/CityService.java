package my.first.project.services;

import my.first.project.entities.City;

import java.util.List;

public interface CityService {

    List<City> getAllCities();

    void saveCity(City city);

    City getCityById(Long id);

    void deleteCityById(Long id);

    City addCity(City city);
}
