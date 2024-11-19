package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.entity.ProductEntity;
import com.RestaurantWebApplication.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Transactional
    public ProductEntity createProduct(ProductEntity productEntity, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes = file.getBytes();
                productEntity.setImage(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Eroare la încărcarea imaginii", e);
            }
        }
        return productRepository.save(productEntity);
    }


    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public ProductEntity updateProduct(Long productId, ProductEntity productEntityToUpdate, MultipartFile file) {
        ProductEntity existingProductEntity = getProductById(productId);
        if (existingProductEntity == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        if (productEntityToUpdate.getName() != null) {
            existingProductEntity.setName(productEntityToUpdate.getName());
        }
        if (productEntityToUpdate.getDescription() != null) {
            existingProductEntity.setDescription(productEntityToUpdate.getDescription());
        }
        if (productEntityToUpdate.getCategory() != null) {
            existingProductEntity.setCategory(productEntityToUpdate.getCategory());
        }
        if (productEntityToUpdate.getPrice() != null) {
            existingProductEntity.setPrice(productEntityToUpdate.getPrice());
        }
        if (file != null && !file.isEmpty()) {
            try {
                existingProductEntity.setImage(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error while uploading the image", e);
            }
        }
        return productRepository.save(existingProductEntity);
    }




}
