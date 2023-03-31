package my.first.project.rest;

import my.first.project.entities.Category;
import my.first.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{id}")
    public Category getCategoryById(@PathVariable(name = "id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
}

    @PutMapping
    public Category updateCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return category;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategoryById(id);
    }
}
