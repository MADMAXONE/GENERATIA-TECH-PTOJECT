package com.RestaurantWebApplication.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {

    @JsonProperty("name")
    private String name;

    @JsonProperty("grade")
    private int grade;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("id")
    private Long id;

}
