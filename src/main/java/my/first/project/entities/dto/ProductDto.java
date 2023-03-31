package my.first.project.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

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

}
