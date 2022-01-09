package htw.webtech.smarthome.service;


import htw.webtech.smarthome.domain.Category;
import htw.webtech.smarthome.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(long id) {
        return categoryRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }

    public void changeCategory(long categoryId, Category categoryChanged) {
        Category category = categoryRepository.getById(categoryId);
        category.setCategoryName(categoryChanged.getCategoryName());
        category.setDescription(categoryChanged.getDescription());
        category.setImageUrl(categoryChanged.getImageUrl());
        categoryRepository.save(category);
    }

    public boolean findById(long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }
}
