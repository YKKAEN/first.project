package my.first.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String productName;
    private double currentPrice;
    private double oldPrice;
    private Boolean newProduct;
    private String productDescription;
    private String productPhoto;
    private String productIsbn;
    private String productDate;
    private String productPages;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
