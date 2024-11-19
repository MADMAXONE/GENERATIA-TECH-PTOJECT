package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<ReviewEntity, Long> {
}
