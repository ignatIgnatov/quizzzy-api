package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.MailRequestDto;
import com.game.quizzzy.dto.request.MessageRequestDto;
import com.game.quizzzy.dto.request.NewPasswordRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.service.MailService;
import com.game.quizzzy.service.UserService;
import com.game.quizzzy.utils.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private static final String SUBJECT = "no-replay";

    private final MailService mailService;
    private final UserService userService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto sendForgotPasswordEmail(@Valid @RequestBody MailRequestDto mailRequestDto) {
        return mailService.sendEmail(
                mailRequestDto.getEmail(),
                SUBJECT,
                MessageService.getNewPasswordLinkTextMessage(mailRequestDto.getEmail()));
    }

    @PostMapping("/ch-pwd")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto sendEmailForChangedPassword(@Valid @RequestBody NewPasswordRequestDto newPasswordRequestDto) {
        return mailService.sendEmail(
                newPasswordRequestDto.getEmail(),
                SUBJECT,
                MessageService.getChangedPasswordTextMessage(newPasswordRequestDto.getEmail(), newPasswordRequestDto.getPassword()));
    }

    @PostMapping("/general-msg")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto sendMessageToAllUsers(@RequestBody MessageRequestDto messageRequestDto) {
        return userService.sendEmailToAllUsers(messageRequestDto);
    }
}
