package com.game.quizzzy.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Question with id " + id + " not found");
    }
}
