package my.first.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "t_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String productPhoto;
    private String productIsbn;
    private String productDate;
    private String productPages;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
