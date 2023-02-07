package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private final String CIRCUIT_BREAKER_NAME = "RATING_HOTEL_SERVICE";

    // create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User tempUser = userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // get user by id
    @GetMapping("/{userId}")
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "circuitBreakerFallBack")
    public ResponseEntity<Object> getUserById(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // get user by id
    @GetMapping
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "circuitBreakerFallBack")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> listUser = userService.getAllUser();
        return ResponseEntity.ok(listUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId) {
        User user = userService.deleteUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        User user1 = userService.updateUser(userId, user);
        return ResponseEntity.ok(user1);
    }

    public ResponseEntity<Object> circuitBreakerFallBack(String userId, Exception e) {
        return ResponseEntity.ok("Sorry, Service is DOWN. Please try again later");

    }
}
