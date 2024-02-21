package com.game.quizzzy.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email, String role) {
        super("User with email " + email + " is already assigned to the " + role + " role.");
    }

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists.");
    }
}
