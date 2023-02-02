package com.user.service.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Resource not found on server !!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
