package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.model.Order;
import com.RestaurantWebApplication.demo.model.OrderWithItems;
import com.RestaurantWebApplication.demo.facade.OrderFacade;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @Autowired
    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderFacade.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderFacade.getOrderById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderWithItems orderWithItems) {
        orderFacade.createOrder(orderWithItems.getOrder(), orderWithItems.getOrderItems());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate(); 
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderFacade.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long orderId, @RequestBody OrderWithItems orderWithItems) {
        orderFacade.updateOrder(orderId, orderWithItems.getOrder(), orderWithItems.getOrderItems());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
