package my.first.project.controllers;

import my.first.project.entities.City;
import my.first.project.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private City city;

    @GetMapping(value = "/cities")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String allCities(Model model) {
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("newCity", city);
        return "cities";
    }

    @GetMapping(value = "/addcity")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getCityForm(Model model) {
        return "cities";
    }

    @PostMapping(value = "/addcity")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addCity(@ModelAttribute(name = "newCity") City newCity) {
        cityService.addCity(newCity);
        return "redirect:/cities";
    }

    @GetMapping(value = "/city/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getEditCityForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("editCity", cityService.getCityById(id));
        return "editCity";
    }

    @PostMapping(value = "/city/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editCity(@ModelAttribute(name = "editCity") City editCity,
                           @PathVariable(name = "id") Long id) {
        editCity.setId(id);
        cityService.saveCity(editCity);
        return "redirect:/cities";
    }
    @GetMapping(value = "/city/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCity(@PathVariable(name = "id") Long id) {
        cityService.deleteCityById(id);
        return "redirect:/cities";
    }
}
