package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.MessageRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.model.User;
import com.game.quizzzy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String SUBJECT = "no-replay";
    private static final String SYSTEM_MESSAGE = "System message from Quizzzy";
    private static final String REGISTRATION_SUBJECT = "Successful registration";

    private final JavaMailSender javaMailSender;
    private final UserServiceImpl userService;

    @Override
    public MessageResponseDto sendForgotPasswordEmail(String email) {
        User user = userService.findByEmail(email);
        MessageResponseDto responseDto = getMessageResponseDto();
        String text = getNewPasswordLinkTextMessage(email);
        sendEmail(user.getEmail(), SUBJECT, text);
        return responseDto;
    }

    @Override
    public MessageResponseDto sendChangedPasswordEmail(String email, String password) {
        User user = userService.findByEmail(email);
        String text = getChangedPasswordTextMessage(email, password);
        sendEmail(user.getEmail(), SUBJECT, text);
        return getMessageResponseDto();
    }

    @Override
    public MessageResponseDto sendMessageToAllUsers(MessageRequestDto messageRequestDto) {
        for (UserResponseDto user : userService.getAllUsers()) {
            sendEmail(user.getEmail(), SYSTEM_MESSAGE, messageRequestDto.getMessage());
        }
        return getMessageResponseToAllUsers();
    }

    @Override
    public MessageResponseDto sendEmailForRegisterUser(String email) {
        sendEmail(email, REGISTRATION_SUBJECT, getMessageForRegisterUser(email));
        return getMessageResponseDto();
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SUBJECT);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    private static MessageResponseDto getMessageResponseDto() {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Email send successfully!");
        return responseDto;
    }

    private MessageResponseDto getMessageResponseToAllUsers() {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        String message = "Email send to " + userService.getAllUsers().size() + " users.";
        messageResponseDto.setMessage(message);
        return messageResponseDto;
    }

    private static String getChangedPasswordTextMessage(String email, String password) {
        return "Hello " + email + ", \n" +
                "\n" +
                "Your password was changed! \n" +
                "The new password is: " + password + "\n" +
                "\n" +
                "This is a system message. Please don't answer it! \n" +
                "\n" +
                "Best regards! \n" +
                "Quizzzy team \n";
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

    private static String getMessageForRegisterUser(String email) {
        return "Hello, " + email + "\n" +
                "\n" +
                "You have successfully registered in Quizzzy - challenge your self.\n" +
                "\n" +
                "We expect you to show knowledge and answer more questions truthfully. \n" +
                "Also, don't forget to join the game with your own questions, which other users will be able to answer.\n" +
                "Good luck! \n" +
                "\n" +
                "\n" +
                "\n" +
                "This is a system message. Please don't answer it! \n" +
                "\n" +
                "Best regards! \n" +
                "Quizzzy team";
    }
}
