package com.user.service.services.impl;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.UserNotFoundException;
import com.user.service.feignclient.service.HotelService;
import com.user.service.feignclient.service.RatingService;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private HotelService hotelService;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // Implementing Rating Service Call: Using Rest Template
        List<User> users = userRepository.findAll();

        for (User userObj : users) {
            setRatingAndHotelForUSer(userObj.getUserId());
        }

        return users;
    }

    @Override
    public User getUser(String userId) {
        User user = setRatingAndHotelForUSer(userId);
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
        deleteUsersRating(userId);
        userRepository.deleteById(userId);
        return user1;
    }

    private User setRatingAndHotelForUSer(String userId) {
        // get user from db with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with specify userId: " + userId));

        // Fetching rating details from UserID
        List<Rating> ratingsOfUser = ratingService.getRating(user.getUserId());

        // Fetching hotel details from RatingId
        List<Rating> ratingsWithHotelDetail = ratingsOfUser.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).toList();

        user.setRating(ratingsWithHotelDetail);

        return user;
    }

    private void deleteUsersRating(String userId) {
        // Deleting ratings by UserId
        ratingService.deleteAllRatingsOfUser(userId);
    }
}
