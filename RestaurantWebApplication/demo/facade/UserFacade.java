package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.UserEntity;
import com.RestaurantWebApplication.demo.model.User;
import com.RestaurantWebApplication.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFacade {
    private final UserService userService;

    @Autowired
    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = new User();
            user.setUsername (userEntity.getUsername());
            user.setPassword (userEntity.getPassword());
            user.setRole (userEntity.getRole());
            users.add(user);
        }
        return users;
    }

    public User getUserById(Long userId) {
        UserEntity userById = userService.getUserById(userId);
        User user = new User();
        user.setUsername(userById.getUsername());
        user.setPassword(userById.getPassword());
        user.setRole(userById.getRole());
        return user;
    }

    public void createUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userService.createUser(userEntity);
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public void updateUser(Long userId, User updatedUser) {

        UserEntity existingUserEntity = userService.getUserById(userId);

        if (existingUserEntity == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        existingUserEntity.setUsername(updatedUser.getUsername());
        existingUserEntity.setPassword(updatedUser.getPassword());
        existingUserEntity.setRole(updatedUser.getRole());

            userService.createUser(existingUserEntity);
    }

    public User findByUsername(String username) {
        UserEntity userEntity = userService.findByUsername(username);
        if (userEntity != null) {
            return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole());
        }
        return null;
    }

}
