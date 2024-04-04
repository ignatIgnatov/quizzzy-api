package com.game.quizzzy.service;

import com.game.quizzzy.dto.response.MessageResponseDto;

public interface MailService {

    MessageResponseDto sendEmail(String to, String subject, String text);
}
