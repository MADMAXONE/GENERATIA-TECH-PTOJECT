package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<OrderEntity, Long>  {
}

