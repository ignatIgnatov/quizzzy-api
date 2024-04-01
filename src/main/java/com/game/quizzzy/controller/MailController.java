package com.game.quizzzy.controller;

import com.game.quizzzy.service.MailService;
import com.game.quizzzy.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class MailController {

    private final UserService userService;

    @PostMapping("/send/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@Valid @PathVariable("email") String email) {
        userService.sendForgotPassword(email);
    }
}
