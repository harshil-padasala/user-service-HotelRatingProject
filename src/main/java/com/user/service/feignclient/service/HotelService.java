package com.user.service.feignclient.service;

import com.user.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Here we don't need to write service calling code. That is the benefit of Feign client

// Making Feign Client and provide name of service
@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

/*
    Here we are passing hotelId and url to get the Hotel details using RestTemplate
    ResponseEntity<Hotel> forEntity =
            restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
    Hotel hotel = forEntity.getBody();
*/
    // Getting Hotel details Using Feign Client
    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable String hotelId);

}
