package com.RestaurantWebApplication.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Reservation {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("reservationDate")
    private LocalDate reservationDate;

    @JsonProperty("tableNumber")
    private Integer tableNumber;

    @JsonProperty("observations")
    private String observations;


}

