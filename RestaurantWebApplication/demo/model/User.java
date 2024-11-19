package com.RestaurantWebApplication.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role;


    public User() {
    }


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
