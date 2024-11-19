package com.RestaurantWebApplication.demo.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_review")
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private int grade;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

}
