package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.facade.ProductFacade;
import com.RestaurantWebApplication.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productFacade.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Product product = productFacade.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") Float price,
            @RequestParam("image") MultipartFile file) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);

            String base64Image = encodeImageToBase64(file);
            product.setImage(base64Image);

            Product createdProduct = productFacade.createProduct(product, file);
            if (createdProduct != null) {
                return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productFacade.deleteProduct(productId);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "price", required = false) Float price,
            @RequestParam(value = "image", required = false) MultipartFile file) {

        try {
            Product productToUpdate = new Product();
            if (name != null) {
                productToUpdate.setName(name);
            }
            if (description != null) {
                productToUpdate.setDescription(description);
            }
            if (category != null) {
                productToUpdate.setCategory(category);
            }
            if (price != null) {
                productToUpdate.setPrice(price);
            }

            String base64Image = null;
            if (file != null && !file.isEmpty()) {
                base64Image = encodeImageToBase64(file);
                productToUpdate.setImage(base64Image);
            }

            Product updatedProduct = productFacade.updateProduct(productId, productToUpdate, file);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String encodeImageToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

}
