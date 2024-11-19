package com.RestaurantWebApplication.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_shopping_cart")
@Getter
@Setter
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_products")
    private Long numberOfProducts;
    @Column(name = "total_price")
    private float totalPrice;


    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ShoppingCartProduct> products = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartEntity)) return false;
        return id != null && id.equals(((ShoppingCartEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}



