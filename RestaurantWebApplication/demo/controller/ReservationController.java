package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.facade.ReservationFacade;
import com.RestaurantWebApplication.demo.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @Autowired
    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationFacade.getAllReservations();
    }

    @GetMapping("/{reservationId}")
    public void getReservation(@PathVariable Long reservationId) {
        reservationFacade.getReservationById(reservationId);
    }

    @GetMapping("/available/{reservationDate}")
    public ResponseEntity<List<Integer>> getAvailableTables(@PathVariable String reservationDate) {
        try {
            LocalDate date = LocalDate.parse(reservationDate);
            List<Integer> availableTables = reservationFacade.getAvailableTables(date);
            return ResponseEntity.ok(availableTables);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping
    public void createReservation(@RequestBody Reservation reservation) {
        reservationFacade.createReservation(reservation);
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId) {
        reservationFacade.deleteReservation(reservationId);
    }

    @DeleteMapping("/date/{reservationDate}")
    public ResponseEntity<?> deleteReservationsByDate(@PathVariable LocalDate reservationDate) {
        reservationFacade.deleteReservationsByDate(reservationDate);
        return ResponseEntity.ok().build();
    }

}
