package com.RestaurantWebApplication.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart_product")
public class ShoppingCartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCartEntity shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    public ShoppingCartProduct() {}

    public ShoppingCartProduct(ShoppingCartEntity shoppingCart, ProductEntity product, Integer quantity) {
        this.shoppingCart = shoppingCart;
        this.product = product;
        this.quantity = quantity;
    }

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public ShoppingCartEntity getShoppingCart() {
        return shoppingCart;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShoppingCart(ShoppingCartEntity shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
