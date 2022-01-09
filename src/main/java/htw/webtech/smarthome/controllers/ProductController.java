package htw.webtech.smarthome.controllers;

import htw.webtech.smarthome.common.APIResponse;
import htw.webtech.smarthome.domain.Category;
import htw.webtech.smarthome.domain.Product;
import htw.webtech.smarthome.dto.ProductDto;
import htw.webtech.smarthome.repository.CategoryRepository;
import htw.webtech.smarthome.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/api/products/{id}")
    public Optional<Product> getProduct(@PathVariable("id") long id){
        return productService.getProduct(id);
    }

    @PostMapping("/api/products")
    public ResponseEntity<APIResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new APIResponse(false, "This category does not exist"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<APIResponse>(new APIResponse(true, "A new product has been successfully created"), HttpStatus.CREATED);

    }

    @PutMapping(path = "/api/products/{id}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable("id") long productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new APIResponse(false, "This category does not exist"), HttpStatus.BAD_REQUEST);
        }
        if (!productService.findProductById(productId)) {
            return new ResponseEntity<>(new APIResponse(false, "This product does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.changeProduct(productDto, productId);
        return new ResponseEntity<>(new APIResponse(true, "This product has been successfully updated"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/products/{id}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long id) {
        boolean successful = productService.deleteById(id);
        if (successful) {
            return new ResponseEntity<>(new APIResponse(true, "This product has been successfully deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new APIResponse(false, "This product does not exist"), HttpStatus.NOT_FOUND);
        }
    }
}
