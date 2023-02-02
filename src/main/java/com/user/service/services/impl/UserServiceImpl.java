package com.user.service.services.impl;

import com.user.service.entities.User;
import com.user.service.exceptions.UserNotFoundException;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with specify userId: " + userId));
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
}
