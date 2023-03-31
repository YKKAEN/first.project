package my.first.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;
}
