package my.first.project.services.impl;

import my.first.project.entities.City;
import my.first.project.repositories.CityRepository;
import my.first.project.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;


    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.getById(id);
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public City createCity(City city) {
        return cityRepository.save(city);
    }
}

