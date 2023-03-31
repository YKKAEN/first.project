package my.first.project.services.impl;

import my.first.project.entities.Country;
import my.first.project.repositories.CountryRepository;
import my.first.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.getById(id);
    }

    @Override
    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }
}

