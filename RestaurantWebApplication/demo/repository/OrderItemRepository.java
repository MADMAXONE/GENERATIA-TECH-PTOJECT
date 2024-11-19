package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.OrderEntity;
import com.RestaurantWebApplication.demo.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Transactional
    void deleteByOrder(OrderEntity order);
}
