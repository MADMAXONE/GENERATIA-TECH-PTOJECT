package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
