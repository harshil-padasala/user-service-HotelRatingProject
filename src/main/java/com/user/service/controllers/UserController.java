package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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

    private final String CIRCUIT_BREAKER_NAME = "RATING_HOTEL_SERVICE_CIRCUIT_BREAKER";

    private final String RETRY_NAME = "RATING_HOTEL_SERVICE_RETRY";

    private final String RATE_LIMITER = "RATING_HOTEL_SERVICE_RATE_LIMITER";

    private int count = 0;

    // create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User tempUser = userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // get user by id
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "circuitBreakerFallBack")
//    @Retry(name = RETRY_NAME)
    @RateLimiter(name = RATE_LIMITER)
    public ResponseEntity<Object> getUserById(@PathVariable String userId) {
        System.out.println("Retry method is called " + count++ + " times.");
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
//    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "circuitBreakerFallBackAllUser")
//    @Retry(name = RETRY_NAME)
    public ResponseEntity<List<User>> getAllUser() {
        List<User> listUser = userService.getAllUser();
        System.out.println(++count + " --> // get users");
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
        return ResponseEntity.ok("Sorry, Service is DOWN. Please try again later...");
    }

    public ResponseEntity<Object> retryFallBack(String userId, Exception e) {
        return ResponseEntity.ok("Sorry, Didn't get response. Please Retry...");
    }

    public ResponseEntity<Object> rateLimiterFallBack(String userId, Exception e) {
        return ResponseEntity.ok("Sorry, Our service is getting too many requests around the world. Please, try again late...");
    }
}
