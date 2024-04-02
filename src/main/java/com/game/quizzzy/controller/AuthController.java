package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.ChangePasswordRequestDto;
import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.LoginResponse;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@Valid @RequestBody UserRequestDto user) {
        return authService.registerUser(user);
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LoginResponse logout() {
        return authService.logout();
    }

    @Operation(summary = "Change password")
    @PutMapping("/ch-pwd")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto) {
        return authService.changePassword(requestDto);
    }
}
