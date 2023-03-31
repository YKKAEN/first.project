package my.first.project.rest;

import my.first.project.entities.Product;
import my.first.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable(name = "id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return product;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProductById(id);
    }
}
