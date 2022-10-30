package my.first.project.services;

import my.first.project.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void saveCategory(Category category);

    Category getCategoryById(Long id);

    void deleteCategoryById(Long id);

    Category createCategory(Category category);

}
