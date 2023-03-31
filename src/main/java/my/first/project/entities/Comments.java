package my.first.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Table(name = "comments")
public class Comments{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    @ManyToOne(fetch =FetchType.LAZY)
    Users users;

}
