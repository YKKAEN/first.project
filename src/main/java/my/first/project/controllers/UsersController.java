package my.first.project.controllers;

import my.first.project.entities.Roles;
import my.first.project.entities.Users;
import my.first.project.services.CityService;
import my.first.project.services.CountryService;
import my.first.project.services.RolesRervice;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesRervice rolesRervice;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;


    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("usersList", usersService.getAllUsers());
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("countryList", countryService.getAllCountries());
        return "users";
    }

    @GetMapping(value = "/users/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editUser(Model model, @PathVariable(name = "id") Long id) {

        Users editUser = usersService.getUsersById(id);
        List<Roles> roles = rolesRervice.getAllRoles();
        roles.removeAll(editUser.getRoles());

        model.addAttribute("unassignedRoles", roles);
        model.addAttribute("editUser", editUser);
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
        model.addAttribute("countryList", countryService.getAllCountries());

        return "editUser";
    }

    @PostMapping(value = "/users/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editUser(@RequestParam(name = "fullName") String fullName,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "address") String address,
                           @RequestParam(name = "phone") String phone,
                           @RequestParam(name = "cityId") Long cityId,
                           @RequestParam(name = "countryId") Long countryId,
                           @PathVariable("id") Long id) {

        Users users = usersService.getUsersById(id);
        users.setFullName(fullName);
        users.setEmail(email);
        users.setPassword(password);
        users.setAddress(address);
        users.setPhone(phone);
        users.setCity(cityService.getCityById(cityId));
        users.setCountry(countryService.getCountryById(countryId));
        usersService.saveUsers(users);
        return "redirect:/users";
    }

    @GetMapping(value = "/users/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        usersService.deleteUsersById(id);
        return "redirect:/users";
    }

    @PostMapping("/users/role-assignee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String roleAssignee(@RequestParam(name = "user_id") Long userId,
                               @RequestParam(name = "role_id") Long roleId) {
        Users user = usersService.getUsersById(userId);
        List<Roles> roles = user.getRoles();
        roles.add(rolesRervice.getRolesById(roleId));
        user.setRoles(roles);
        usersService.saveUsers(user);
        return "redirect:/users/edit/" + userId;
    }

    @PostMapping("/users/role-unassignee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String roleUnassignee(@RequestParam(name = "user_id") Long userId,
                                 @RequestParam(name = "role_id") Long roleId) {
        Users user = usersService.getUsersById(userId);
        List<Roles> userRoles = user.getRoles();
        userRoles.remove(rolesRervice.getRolesById(roleId));
        user.setRoles(userRoles);
        usersService.saveUsers(user);
        return "redirect:/users/edit/" + userId;
    }
}