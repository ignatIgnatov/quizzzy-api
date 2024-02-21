package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.LoginResponse;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRequestDto user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LoginResponse logout() {
        return authService.logout();
    }
}
