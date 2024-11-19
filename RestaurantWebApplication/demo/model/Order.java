package com.RestaurantWebApplication.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("dateAndTime")
    private LocalDateTime dateAndTime;

    @JsonProperty("name")
    private String name;

    @JsonProperty("numberPhone")
    private String numberPhone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("observations")
    private String observations;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    @JsonProperty("orderItems")
    private List<OrderItem> orderItems;

}
