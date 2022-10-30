package my.first.project.controllers;

import my.first.project.entities.Users;
import my.first.project.services.CategoryService;
import my.first.project.services.CityService;
import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private Users newUser;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CategoryService categoryService;


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

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(name = "firstName") String firstName,
                               @RequestParam(name = "lastName") String lastName,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name = "password") String password) {

        Users newUser = new Users();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);

        if (usersService.createUser(newUser) != null) {
            return "redirect:/login?regSuccess";
        }
        return "redirect:/register?emailError";
    }

    @GetMapping(value = "/account")
    @PreAuthorize("isAuthenticated()")
    public String account(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        return "account";
    }

    @GetMapping(value = "/password")
    @PreAuthorize("isAuthenticated()")
    public String editPassword(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        return "editPassword";
    }

    @PostMapping(value = "/update-password/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam("id") Long id,
                                 @RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "retypeNewPassword") String retypeNewPassword) {

        Users users = usersService.getUsersById(id);
        if (users.getPassword().equals(bCryptPasswordEncoder.encode(oldPassword))) {
            if (newPassword.equals(retypeNewPassword)) {
                users.setPassword(newPassword);
                return "redirect:/account";
            }
        }
        return "redirect:/account?error";
    }
}
