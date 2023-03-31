package my.first.project.services;

import my.first.project.entities.Country;
import my.first.project.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    void saveCountry(Country country);

    Country getCountryById(Long id);

    void deleteCountryById(Long id);

    Country addCountry(Country country);
}
