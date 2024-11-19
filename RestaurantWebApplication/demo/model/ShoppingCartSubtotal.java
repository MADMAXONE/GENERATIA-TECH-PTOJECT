package com.RestaurantWebApplication.demo.model;

public class ShoppingCartSubtotal {
    private float totalPrice;
    private long totalItems;

    public ShoppingCartSubtotal(float totalPrice, long totalItems) {
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public long getTotalItems() {
        return totalItems;
    }
}
