package com.RestaurantWebApplication.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    @JsonProperty("products")
    private List<ProductInCart> products;

    @JsonProperty("totalPrice")
    private float totalPrice;

    @JsonProperty("numberOfProducts")
    private Long numberOfProducts;

    @Data
    public static class ProductInCart {
        private Long productId;
        private String productName;
        private Float price;
        private Integer quantity;
    }

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.totalPrice = 0.0f;
        this.numberOfProducts = 0L;
    }


    public void addProduct(ProductInCart product) {
        if (product != null) {
            this.products.add(product);
            this.totalPrice += product.getPrice() * product.getQuantity();
            this.numberOfProducts += product.getQuantity();
        }
    }
}
