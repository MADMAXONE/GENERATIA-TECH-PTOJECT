package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.ProductEntity;
import com.RestaurantWebApplication.demo.model.Product;
import com.RestaurantWebApplication.demo.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ProductFacade {

    private final ProductService productService;

    @Autowired
    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productService.getAllProducts();
        List<Product> products = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            Product product = convertToProductDTO(productEntity);
            products.add(product);
        }
        return products;
    }

    @Transactional
    public Product getProductById(Long productId) {
        ProductEntity productEntity = productService.getProductById(productId);
        return convertToProductDTO(productEntity);
    }

    @Transactional
    public Product createProduct(Product product, MultipartFile file) throws IOException {
        ProductEntity productEntity = convertToProductEntity(product, file);
        ProductEntity savedEntity = productService.createProduct(productEntity, file);
        return convertToProductDTO(savedEntity);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productService.deleteProduct(productId);
    }

    @Transactional
    public Product updateProduct(Long productId, Product productData, MultipartFile file) throws IOException {
        ProductEntity existingProductEntity = productService.getProductById(productId);
        if (existingProductEntity == null) {
            throw new RuntimeException("Produsul nu a fost găsit pentru ID: " + productId);
        }

        ProductEntity productEntityToUpdate = convertToProductEntity(productData, file);

        ProductEntity updatedEntity = productService.updateProduct(productId, productEntityToUpdate, file);
        return convertToProductDTO(updatedEntity);
    }


    private Product convertToProductDTO(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setPrice(productEntity.getPrice());
        if (productEntity.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(productEntity.getImage());
            product.setImage(base64Image);
        }
        return product;
    }

    private ProductEntity convertToProductEntity(Product product, MultipartFile file) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        if (product.getName() != null) {
            productEntity.setName(product.getName());
        }
        if (product.getDescription() != null) {
            productEntity.setDescription(product.getDescription());
        }
        if (product.getCategory() != null) {
            productEntity.setCategory(product.getCategory());
        }
        if (product.getPrice() != null) {
            productEntity.setPrice(product.getPrice());
        }
        if (file != null && !file.isEmpty()) {
            productEntity.setImage(file.getBytes());
        } else if (product.getImage() != null && !product.getImage().isEmpty()) {
            productEntity.setImage(Base64.getDecoder().decode(product.getImage()));
        }
        return productEntity;
    }
}
