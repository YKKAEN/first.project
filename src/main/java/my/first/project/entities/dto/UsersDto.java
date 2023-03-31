package my.first.project.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.first.project.entities.Roles;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsersDto {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;

    private List<Roles> roles;
}