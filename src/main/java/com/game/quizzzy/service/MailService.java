package com.game.quizzzy.service;

public interface MailService {
    void sendEmail(String to, String subject, String text);
}
