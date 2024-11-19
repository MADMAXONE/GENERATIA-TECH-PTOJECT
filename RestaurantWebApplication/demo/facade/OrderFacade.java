package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.OrderEntity;
import com.RestaurantWebApplication.demo.entity.OrderItemEntity;
import com.RestaurantWebApplication.demo.model.Order;
import com.RestaurantWebApplication.demo.model.OrderItem;
import com.RestaurantWebApplication.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderFacade {

    private final OrderService orderService;

    @Autowired
    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<Order> getAllOrders() {
        List<OrderEntity> orderEntities = orderService.getAllOrders();
        return orderEntities.stream()
                .map(this::convertToOrderModel)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        OrderEntity orderEntity = orderService.getOrderById(orderId);
        return orderEntity != null ? convertToOrderModel(orderEntity) : null;
    }

    @Transactional
    public void createOrder(Order order, List<OrderItem> orderItems) {
        OrderEntity orderEntity = convertToOrderEntity(order);
        List<OrderItemEntity> orderItemEntities = orderItems.stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());

        orderService.createOrder(orderEntity, orderItemEntities);
    }

    @Transactional
    public void updateOrder(Long orderId, Order order, List<OrderItem> orderItems) {
        OrderEntity orderEntity = convertToOrderEntity(order);
        List<OrderItemEntity> orderItemEntities = orderItems.stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());

        orderService.updateOrder(orderId, orderEntity, orderItemEntities);
    }

    public void deleteOrder(Long orderId) {
        orderService.deleteOrder(orderId);
    }

    private Order convertToOrderModel(OrderEntity orderEntity) {
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setDateAndTime(orderEntity.getDateAndTime());
        order.setName(orderEntity.getName());
        order.setNumberPhone(orderEntity.getNumberPhone());
        order.setAddress(orderEntity.getAddress());
        order.setObservations(orderEntity.getObservations());
        order.setTotalPrice(orderEntity.getTotalPrice());

        List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
                .map(this::convertToOrderItemModel)
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);

        return order;
    }

    private OrderItem convertToOrderItemModel(OrderItemEntity orderItemEntity) {
        return new OrderItem(
                orderItemEntity.getProductName(),
                orderItemEntity.getQuantity(),
                orderItemEntity.getPrice()
        );
    }

    private OrderEntity convertToOrderEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName(order.getName());
        orderEntity.setNumberPhone(order.getNumberPhone());
        orderEntity.setAddress(order.getAddress());
        orderEntity.setObservations(order.getObservations());
        orderEntity.setTotalPrice(order.getTotalPrice());
        return orderEntity;
    }

    private OrderItemEntity convertToOrderItemEntity(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProductName(orderItem.getProductName());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setPrice(orderItem.getPrice());
        return orderItemEntity;
    }

}
