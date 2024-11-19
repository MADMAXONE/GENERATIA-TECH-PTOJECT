package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.facade.UserFacade;
import com.RestaurantWebApplication.demo.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userFacade.getUserById(userId);
    }

    @GetMapping("/protected-resource")
    public ResponseEntity<?> getProtectedResource(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.ok("Access granted for role: " + user.getRole());
    }


    @PostMapping
    public void createUser(@RequestBody User user) {
         userFacade.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData, HttpSession session) {
        User user = userFacade.findByUsername(loginData.getUsername());
        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            if (!user.getRole().equals(loginData.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Rolul selectat nu corespunde cu contul utilizatorului.\"}");
            }
            session.setAttribute("user", user);
            return ResponseEntity.ok("{\"message\":\"Conectarea s-a realizat cu succes!\", \"role\":\"" + user.getRole() + "\"}");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Invalid credentials\"}");
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("User logged out successfully!");
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userFacade.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
         userFacade.updateUser(userId, updatedUser);
    }
}

