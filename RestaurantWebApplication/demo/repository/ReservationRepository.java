package com.RestaurantWebApplication.demo.repository;

import com.RestaurantWebApplication.demo.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByReservationDate(LocalDate reservationDate);
}
