package com.game.quizzzy.service;

import com.game.quizzzy.dto.response.MessageResponseDto;

public interface MailService {
    MessageResponseDto sendForgotPasswordEmail(String email);

    MessageResponseDto sendChangedPasswordEmail(String email, String password);
}
