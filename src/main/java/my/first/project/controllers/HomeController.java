package my.first.project.controllers;

import my.first.project.entities.Roles;
import my.first.project.entities.Users;
import my.first.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "index";
    }

    @GetMapping(value = "/index/{categoryId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllProductsByCategoryId(Model model, @PathVariable(name = "categoryId") Long categoryId) {
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("categoryList", categoryService.getCategoryById(categoryId));
        model.addAttribute("currentUser", usersService.getUserData());
        return "products";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        return "/login";
    }

    @GetMapping(value = "/403")
    public String errorPage(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        return "403";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("countryList", countryService.getAllCountries());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "fullName") String fullName,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "phone") String phone,
                           @RequestParam(name = "address") String address,
                           @RequestParam(name = "countryId") Long countryId,
                           @RequestParam(name = "cityId") Long cityId) {

        Users newUser = new Users();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setCountry(countryService.getCountryById(countryId));
        newUser.setCity(cityService.getCityById(cityId));

        if (usersService.createUser(newUser) != null) {
            return "redirect:/login?regSuccess";
        }
        return "redirect:/register?emailError";
    }

    @GetMapping(value = "/user/account")
    @PreAuthorize("isAuthenticated()")
    public String userAccount(Model model) {

        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("countryList", countryService.getAllCountries());
        return "userAccount";
    }

    @GetMapping(value = "/user/edit-account/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editUserAccount(Model model, @PathVariable Long id) {

        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("countryList", countryService.getAllCountries());
        return "editUserAccount";
    }

    @PostMapping(value = "/user/edit-account/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editUserAccount(@RequestParam(name = "fullName") String fullName,
                                  @RequestParam(name = "address") String address,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "phone") String phone,
                                  @RequestParam(name = "cityId") Long cityId,
                                  @RequestParam(name = "countryId") Long countryId,
                                  @PathVariable("id") Long id) {

        Users users = usersService.getUsersById(id);
        users.setFullName(fullName);
        users.setEmail(email);
        users.setAddress(address);
        users.setPhone(phone);
        users.setCity(cityService.getCityById(cityId));
        users.setCountry(countryService.getCountryById(countryId));
        usersService.saveUsers(users);
        return "redirect:/user/account";
    }

    @GetMapping(value = "/user/edit-password/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editUserPassword(Model model, @PathVariable String id) {
        model.addAttribute("currentUser", usersService.getUserData());
        return "editUserPassword";
    }


    @PostMapping(value = "/user/edit-password/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editUserPassword(@PathVariable(name = "id") Long id,
                                   @RequestParam(name = "oldPassword") String oldPassword,
                                   @RequestParam(name = "newPassword") String newPassword,
                                   @RequestParam(name = "confirmNewPassword") String confirmNewPassword) {

        Users users = usersService.getUsersById(id);
        if (bCryptPasswordEncoder.matches(oldPassword, users.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                users.setPassword(bCryptPasswordEncoder.encode(newPassword));
                usersService.saveUsers(users);
                return "redirect:/user/account";
            }
        }
        return "redirect:/user/account?error";
    }
}
