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

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_photo")
    private String productPhoto;

    @Column(name = "product_isbn")
    private String productIsbn;

    @Column(name = "product_date")
    private String productDate;

    @Column(name = "product_pages")
    private String productPages;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
