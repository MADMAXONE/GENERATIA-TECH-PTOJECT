package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.repository.ReservationRepository;
import com.RestaurantWebApplication.demo.entity.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationEntity> getAllReservations() {
        return reservationRepository.findAll();
    }

    public ReservationEntity getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public List<Integer> getAvailableTables(LocalDate reservationDate) {
        List<ReservationEntity> reservationsForDate = reservationRepository.findByReservationDate(reservationDate);
        return IntStream.rangeClosed(1, 9)
                .filter(i -> reservationsForDate.stream()
                        .noneMatch(reservation -> reservation.getTableNumber().equals(i)))
                .boxed()
                .collect(Collectors.toList());
    }

    public ReservationEntity createReservation(ReservationEntity reservationEntity) {
        return reservationRepository.save(reservationEntity);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }


    public void deleteReservationsByDate(LocalDate reservationDate) {
        List<ReservationEntity> reservationsToDelete = reservationRepository.findByReservationDate(reservationDate);
        for(ReservationEntity reservation : reservationsToDelete) {
            reservationRepository.delete(reservation);
        }
    }

}
