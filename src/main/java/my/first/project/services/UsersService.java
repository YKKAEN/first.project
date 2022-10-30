package my.first.project.services;

import my.first.project.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {

    Users getUserByEmail(String email);

    Users getUserData();

    List<Users> getAllUsers();

    void saveUsers(Users users);

    Users getUsersById(Long id);

    void deleteUsersById(Long id);

    Users createUser(Users user);

}
