package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
