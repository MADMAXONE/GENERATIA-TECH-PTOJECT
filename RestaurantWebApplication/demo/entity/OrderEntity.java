package com.RestaurantWebApplication.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_order")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_and_time", nullable = false)
    private LocalDateTime dateAndTime = LocalDateTime.now();

    @Column(name = "name")
    private String name;

    @Column(name = "number_phone", length = 10)
    private String numberPhone;

    @Column(name = "address")
    private String address;

    @Column(name = "observations")
    private String observations;

    @Column(name = "total_price")
    private Double totalPrice;


    @PrePersist
    @PreUpdate
    private void validatePhoneNumber() {
        if (numberPhone == null || numberPhone.length() != 10) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

}
