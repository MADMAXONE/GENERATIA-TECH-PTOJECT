package com.RestaurantWebApplication.demo;

import com.RestaurantWebApplication.demo.entity.UserEntity;
import com.RestaurantWebApplication.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new UserEntity("bucatar", "parola123", "BUCĂTAR"));
            userRepository.save(new UserEntity("ospatar", "parola123", "OSPĂTAR"));
            userRepository.save(new UserEntity("administrator", "parola123", "ADMINISTRATOR"));
        }
    }
}
