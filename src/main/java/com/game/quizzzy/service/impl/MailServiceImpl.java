package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String SUBJECT = "no-replay";

    private final JavaMailSender javaMailSender;

    public MessageResponseDto sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SUBJECT);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        return getMessageResponseDto();
    }

    private static MessageResponseDto getMessageResponseDto() {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Email send successfully!");
        return responseDto;
    }
}
