package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.MailRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto sendEmail(@Valid @RequestBody MailRequestDto mailRequestDto) {
        return mailService.sendForgotPasswordMail(mailRequestDto.getEmail());
    }
}
