package com.game.quizzzy.exception;

public class NoAuthenticatedUserException extends RuntimeException {
    public NoAuthenticatedUserException() {
        super("No authenticated user found.");
    }
}
