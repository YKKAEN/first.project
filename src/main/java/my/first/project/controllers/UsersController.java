package my.first.project.controllers;

import my.first.project.entities.Roles;
import my.first.project.entities.Users;
import my.first.project.services.CityService;
import my.first.project.services.RolesRervice;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
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


    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("usersList", usersService.getAllUsers());
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("cityList", cityService.getAllCities());
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

        return "editUser";
    }

    @PostMapping(value = "/users/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editUser(@RequestParam(name = "firstName") String firstName,
                           @RequestParam(name = "lastName") String lastName,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "phone") String phone,
                           @RequestParam(name = "address") String address,
                           @RequestParam(name = "cityId") Long cityId,
                           @PathVariable("id") Long id) {

        Users users = usersService.getUsersById(id);
        users.setFirstName(firstName);
        users.setLastName(lastName);
        users.setEmail(email);
        users.setPhone(phone);
        users.setAddress(address);
        users.setCity(cityService.getCityById(cityId));
        List<Roles> roles = rolesRervice.getAllRoles();
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

    @PostMapping(value = "/edit-user-detail/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editUserDetail(@RequestParam(name = "firstName") String firstName,
                                 @RequestParam(name = "lastName") String lastName,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "phone") String phone,
                                 @RequestParam(name = "address") String address,
                                 @RequestParam(name = "cityId") Long cityId,
                                 @PathVariable("id") Long id) {

        Users users = usersService.getUsersById(id);
        users.setFirstName(firstName);
        users.setLastName(lastName);
        users.setEmail(email);
        users.setPhone(phone);
        users.setAddress(address);
        users.setCity(cityService.getCityById(cityId));
        usersService.saveUsers(users);

        return "redirect:/account";
    }
}

//    @PostMapping(value = "/edituser/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public String editUser(@RequestParam(name = "firstName") String firstName,
//                           @RequestParam(name = "lastName") String lastName,
//                           @RequestParam(name = "email") String email,
//                           @RequestParam(name = "address") String address,
//                           @RequestParam(name = "phone") String phone,
//                           @RequestParam("id") Long id,
//                           @RequestParam(name = "oldPassword") String oldPassword,
//                           @RequestParam(name = "newPassword") String newPassword,
//                           @RequestParam(name = "retypeNewPassword") String retypeNewPassword) {
//
//        Users users = usersService.getUsersById(id);
//        if (users.getPassword().equals(bCryptPasswordEncoder.encode(oldPassword))) {
//            if (newPassword.equals(retypeNewPassword)) {
//                users.setPassword(newPassword);
//                users.setEmail(email);
//                users.setFirstName(firstName);
//                users.setLastName(lastName);
//                users.setAddress(address);
//                users.setPhone(phone);
//                return "redirect:/account";
//            }
//        }
//        return "redirect:/account?error";
//    }
//}