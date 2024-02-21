package com.game.quizzzy.exception;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String role) {
        super(role + " role already exists.");
    }
}
