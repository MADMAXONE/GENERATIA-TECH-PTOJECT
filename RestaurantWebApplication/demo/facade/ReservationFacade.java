package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.ReservationEntity;
import com.RestaurantWebApplication.demo.model.Reservation;
import com.RestaurantWebApplication.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationFacade {

    private final ReservationService reservationService;

    @Autowired
    public ReservationFacade(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public List<Reservation> getAllReservations() {
        List<ReservationEntity> reservationEntities = reservationService.getAllReservations();
        List<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities){
            Reservation reservation = new Reservation();
            reservation.setId(reservationEntity.getId());
            reservation.setName(reservationEntity.getName());
            reservation.setPhoneNumber(reservationEntity.getPhoneNumber());
            reservation.setReservationDate(reservationEntity.getReservationDate());
            reservation.setTableNumber(reservationEntity.getTableNumber());
            reservation.setObservations(reservationEntity.getObservations());
            reservations.add(reservation);
        }
        return reservations;
    }

    public Reservation getReservationById(Long reservationId) {
        ReservationEntity reservationById = reservationService.getReservationById(reservationId);
        Reservation reservation = new Reservation();
        reservation.setId(reservationById.getId());
        reservation.setName(reservationById.getName());
        reservation.setPhoneNumber(reservationById.getPhoneNumber());
        reservation.setReservationDate(reservationById.getReservationDate());
        reservation.setTableNumber(reservationById.getTableNumber());
        reservation.setObservations(reservationById.getObservations());

        return reservation;
    }

    public List<Integer> getAvailableTables(LocalDate reservationDate) {
        return reservationService.getAvailableTables(reservationDate);
    }

    public void createReservation(Reservation reservation) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setName(reservation.getName());
        reservationEntity.setPhoneNumber(reservation.getPhoneNumber());
        reservationEntity.setReservationDate(reservation.getReservationDate());
        reservationEntity.setTableNumber(reservation.getTableNumber());
        reservationEntity.setObservations(reservation.getObservations());
        reservationService.createReservation(reservationEntity);
    }

    public void deleteReservation(Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }


    public void deleteReservationsByDate(LocalDate reservationDate) {
        reservationService.deleteReservationsByDate(reservationDate);
    }

}
