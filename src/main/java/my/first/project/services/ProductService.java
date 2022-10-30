package my.first.project.services;

import my.first.project.entities.Product;
import my.first.project.entities.Roles;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void saveProduct(Product product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product createProduct(Product product);

    List<Product> getAllByCategoryId(Long categoryId);

}