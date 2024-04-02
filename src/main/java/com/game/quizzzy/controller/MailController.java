package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.MailRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/mail")
@RequiredArgsConstructor
public class MailController {

    private final UserService userService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto sendEmail(@Valid @RequestBody MailRequestDto mailRequestDto) {
        return userService.sendForgotPassword(mailRequestDto.getEmail());
    }
}
