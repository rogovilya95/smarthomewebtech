package htw.webtech.smarthome.controllers;

import htw.webtech.smarthome.common.APIResponse;
import htw.webtech.smarthome.domain.Category;
import htw.webtech.smarthome.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/api/categories/{id}")
    public Optional<Category> getCategory(@PathVariable("id") long id){
        return categoryService.getCategory(id);
    }

    @PostMapping("/api/categories")
    public ResponseEntity<APIResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new APIResponse(true, "A new category has been successfully created"), HttpStatus.CREATED);
    }

    @PutMapping(path = "/api/categories/{id}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable("id") long categoryId, @RequestBody Category category) {
        if (!categoryService.findById(categoryId)) {
            return new ResponseEntity<APIResponse>(new APIResponse(false, "This category does not exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.changeCategory(categoryId, category);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "This category has been successfully updated"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/categories/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        boolean successful = categoryService.deleteById(id);
        if (successful) {
            return new ResponseEntity<APIResponse>(new APIResponse(true, "This category has been successfully deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<APIResponse>(new APIResponse(false, "This category does not exist"), HttpStatus.NOT_FOUND);
        }
    }




}
