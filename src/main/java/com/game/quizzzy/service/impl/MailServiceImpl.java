package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.model.User;
import com.game.quizzzy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final UserServiceImpl userService;

    @Override
    public MessageResponseDto sendForgotPasswordMail(String email) {
        User user = userService.findByEmail(email);
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Email send successfully!");
        String text = getNewPasswordLinkTextMessage(email);
        sendEmail(user.getEmail(), "no-replay", text);
        return responseDto;
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@quyzzzy.game");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    private static String getNewPasswordLinkTextMessage(String email) {
        return "Hello " + email + ", \n" +
                "\n" +
                "You are receiving this message because you have requested to change your password. \n" +
                "Follow the instructions to change your password. \n" +
                "\n" +
                "Click on this link to add your new password: \n" +
                "http://localhost:3000/forgot-password \n" +
                "\n" +
                "\n" +
                "\n" +
                "This is a system message. Please don't answer it! \n" +
                "\n" +
                "Best regards! \n" +
                "Quizzzy team";
    }
}
