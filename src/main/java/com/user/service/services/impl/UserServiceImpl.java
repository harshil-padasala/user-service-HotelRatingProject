package com.user.service.services.impl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.UserNotFoundException;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // Implementing Rating Service Call: Using Rest Template
        List<User> users = userRepository.findAll();

        for (User user : users) {
            // fetch rating of the above user from RATING-SERVICE
            // http://localhost:8083/ratings/user-id/{user-id}
            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user-id/" + user.getUserId(), Rating[].class);
            setRatingAndHotelDataForUser(user, ratingsOfUser);
        }

        return users;
    }

    @Override
    public User getUser(String userId) {
        // get user from db with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with specify userId: " + userId));

        // fetch rating of the above user from RATING-SERVICE
        // http://localhost:8083/ratings/user-id/{user-id}
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user-id/" + userId, Rating[].class);
        setRatingAndHotelDataForUser(user, ratingsOfUser);

        return user;
    }

    @Override
    public User updateUser(String userId, User user) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with specify userId: " + userId));
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(String userId) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with specify userId: " + userId));
        userRepository.deleteById(userId);
        return user1;
    }

    // method which communicate with RATING-SERVICE and HOTEL-SERVICE
    private void setRatingAndHotelDataForUser(User user, Rating[] ratingsOfUser) {

        // extract list from array
        List<Rating> ratingList = Arrays.stream(ratingsOfUser).toList();

        // set Hotel data in Rating Object
        List<Rating> ratings = ratingList.stream().map(rating -> {
            // api call to hotel service to get the hotel
            // http://localhost:8082/hotels/3e619564-7880-4544-b7e3-278e9fd5b2cc
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();

            // set the hotel in rating obj
            rating.setHotel(hotel);

            // return the rating
            return rating;
        }).toList();

        // set users all ratings in user obj
        user.setRating(ratings);
    }
}
