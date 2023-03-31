package my.first.project.controllers;

import my.first.project.entities.City;
import my.first.project.entities.Country;
import my.first.project.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private Country country;

    @GetMapping(value = "/countries")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String allCountries(Model model) {
        model.addAttribute("countryList", countryService.getAllCountries());
        model.addAttribute("newCountry", country);
        return "countries";
    }

    @GetMapping(value = "/addcountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getCountryForm(Model model) {
        return "countries";
    }

    @PostMapping(value = "/addcountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addCountry(@ModelAttribute(name = "newCountry") Country newCountry) {
        countryService.addCountry(newCountry);
        return "redirect:/countries";
    }

    @GetMapping(value = "/country/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getEditCountryForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("editCountry", countryService.getCountryById(id));
        return "editCountry";
    }

    @PostMapping(value = "/country/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editCountry(@ModelAttribute(name = "editCountry") Country editCountry,
                           @PathVariable(name = "id") Long id) {
        editCountry.setId(id);
        countryService.saveCountry(editCountry);
        return "redirect:/countries";
    }
    @GetMapping(value = "/country/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCountry(@PathVariable(name = "id") Long id) {
        countryService.deleteCountryById(id);
        return "redirect:/countries";
    }
}
