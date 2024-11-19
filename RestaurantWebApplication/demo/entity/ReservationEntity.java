package com.RestaurantWebApplication.demo.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "t_reservation" )
@Data
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "observations")
    private String observations;

    @PrePersist
    @PreUpdate
    private void validatePhoneNumber() {
        if (phoneNumber == null || phoneNumber.length() != 10) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}

