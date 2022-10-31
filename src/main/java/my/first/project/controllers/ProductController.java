package my.first.project.controllers;

import my.first.project.entities.Category;
import my.first.project.entities.Product;
import my.first.project.services.CategoryService;
import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProductController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Product product;

    @Value("${file.photo.upload}")
    private String uploadPath;

    @Value("${file.photo.upload.build}")
    private String uploadPathBuild;

    @Value("${file.photo.view}")
    private String viewPath;

    @Value("${file.photo.default}")
    private String defaultPhoto;

    @GetMapping(value = "/products")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String allProducts(Model model) {
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("newProduct", product);
        return "products";
    }

    @GetMapping(value = "/addproduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getProductForm(Model model) {
        return "products";
    }

    @PostMapping(value = "/addproduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addProduct(@RequestParam(name = "productPhoto") MultipartFile file,
                             @RequestParam(name = "productName") String productName,
                             @RequestParam(name = "productPrice") BigDecimal productPrice,
                             @RequestParam(name = "productDescription") String productDescription,
                             @RequestParam(name = "categoryId") Long categoryId,
                             @RequestParam(name = "productIsbn") String productIsbn,
                             @RequestParam(name = "productDate") String productDate,
                             @RequestParam(name = "productPages") String productPages) {

        Product product = new Product();
        String productPhoto = uploadPhoto(file);
        product.setProductPhoto(productPhoto);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductDescription(productDescription);
        product.setCategory(categoryService.getCategoryById(categoryId));
        product.setProductIsbn(productIsbn);
        product.setProductDate(productDate);
        product.setProductPages(productPages);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/view-photo/{photoName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] viewProductPhoto(@PathVariable(name = "photoName") String photoName) throws IOException {
        String photourl = viewPath + defaultPhoto;
        if (photoName != null) {
            photourl = viewPath + photoName + ".jpg";
        }
        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource(photourl);
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(viewPath + defaultPhoto);
            inputStream = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping(value = "/product/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getEditProductForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("editProduct", productService.getProductById(id));
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("currentUser", usersService.getUserData());
        return "editProduct";
    }

    @PostMapping(value = "/product/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editProduct(@RequestParam(name = "productPhoto") MultipartFile file,
                              @RequestParam(name = "productName") String productName,
                              @RequestParam(name = "productPrice") BigDecimal productPrice,
                              @RequestParam(name = "productDescription") String productDescription,
                              @RequestParam(name = "categoryId") Long categoryId,
                              @RequestParam(name = "productIsbn") String productIsbn,
                              @RequestParam(name = "productDate") String productDate,
                              @RequestParam(name = "productPages") String productPages,
                              @RequestParam(name = "id") Long id) {

        Product product = productService.getProductById(id);

        if (!file.isEmpty()) {
            product.setProductPhoto(uploadPhoto(file));
        }

        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductDescription(productDescription);
        product.setCategory(categoryService.getCategoryById(categoryId));
        product.setProductIsbn(productIsbn);
        product.setProductDate(productDate);
        product.setProductPages(productPages);
        productService.saveProduct(product);
        return "redirect:/products";

    }

    @GetMapping(value = "/product/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping(value = "/details/{id}")
    public String getProductDetails(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("productList", productService.getProductById(id));
        model.addAttribute("currentUser", usersService.getUserData());
        return "productDetails";
    }

    public String uploadPhoto(MultipartFile file) {
        String photoName = DigestUtils.sha1Hex("photo" + file.getOriginalFilename());

        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {

            try {
                Path path = Paths.get(uploadPath + photoName + ".jpg");
                Path pathBuild = Paths.get(uploadPathBuild + photoName + ".jpg");
                byte[] bytes = file.getBytes();
                Files.write(path, bytes);
                Files.write(pathBuild, bytes);
                return photoName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}