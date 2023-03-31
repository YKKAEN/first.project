package my.first.project.controllers;

import my.first.project.entities.Category;
import my.first.project.services.CategoryService;
import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Category category;

    @GetMapping(value = "/categories")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String allCategories(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategories());
        model.addAttribute("newCategory", category);
        return "categories";
    }

    @GetMapping(value = "/addcategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getCategoryForm(Model model) {
        return "categories";
    }

    @PostMapping(value = "/addcategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addCategory(@ModelAttribute(name = "newCategory") Category newCategory) {
        categoryService.addCategory(newCategory);
        return "redirect:/categories";
    }

    @GetMapping(value = "/category/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getEditCategoryForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("editCategory", categoryService.getCategoryById(id));
        return "editCategory";
    }

    @PostMapping(value = "/category/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editCategory(@ModelAttribute(name = "editCategory") Category editCategory,
                              @PathVariable(name = "id") Long id) {
        editCategory.setId(id);
        categoryService.saveCategory(editCategory);
        return "redirect:/categories";
    }

    @GetMapping(value = "/category/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategoryById(id);
        return  "redirect:/categories";
    }
    @GetMapping(value = "/bestsellers")
    public String bestellers(Model model) {
        return "/bestsellers";
    }

    @GetMapping(value = "/bestsellers/{categoryId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllProductsByCategoryId(Model model, @PathVariable(name = "categoryId") Long categoryId) {
        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("categoryList", categoryService.getCategoryById(categoryId));
        model.addAttribute("currentUser", usersService.getUserData());
        return "products";
    }
}