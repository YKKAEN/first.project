package my.first.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "t_users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;
}
