package com.RestaurantWebApplication.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_product")
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Float price;

    @Column(name = "image", columnDefinition="bytea")
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        return id != null && id.equals(((ProductEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
