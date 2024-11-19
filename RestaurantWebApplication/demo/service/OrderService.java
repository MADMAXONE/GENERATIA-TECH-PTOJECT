package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.entity.OrderEntity;
import com.RestaurantWebApplication.demo.entity.OrderItemEntity;
import com.RestaurantWebApplication.demo.repository.OrderItemRepository;
import com.RestaurantWebApplication.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public OrderEntity createOrder(OrderEntity orderEntity, List<OrderItemEntity> orderItems) {
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        orderItems.forEach(item -> {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        });
        return savedOrder;
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public OrderEntity updateOrder(Long orderId, OrderEntity updatedOrderEntity, List<OrderItemEntity> orderItems) {

        OrderEntity existingOrderEntity = getOrderById(orderId);
        if (existingOrderEntity == null) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }


        existingOrderEntity.setName(updatedOrderEntity.getName());
        existingOrderEntity.setNumberPhone(updatedOrderEntity.getNumberPhone());
        existingOrderEntity.setAddress(updatedOrderEntity.getAddress());
        existingOrderEntity.setObservations(updatedOrderEntity.getObservations());
        existingOrderEntity.setTotalPrice(updatedOrderEntity.getTotalPrice());


        orderItemRepository.deleteByOrder(existingOrderEntity);

        orderItems.forEach(item -> {
            item.setOrder(existingOrderEntity);
            orderItemRepository.save(item);
        });


        return orderRepository.save(existingOrderEntity);
    }


}
