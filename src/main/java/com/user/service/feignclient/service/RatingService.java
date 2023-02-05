package com.user.service.feignclient.service;

import com.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    // Getting Rating details Using Feign Client
    @GetMapping("/ratings/user-id/{user-id}")
    List<Rating> getRating(@PathVariable("user-id") String userId);
}
