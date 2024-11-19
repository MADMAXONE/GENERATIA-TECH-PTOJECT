package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.entity.UserEntity;
import com.RestaurantWebApplication.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
