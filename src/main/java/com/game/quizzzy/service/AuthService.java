package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.ChangePasswordRequestDto;
import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.LoginResponse;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;

public interface AuthService {
    UserResponseDto registerUser(UserRequestDto user);

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse logout();

    UserResponseDto getCurrentUser();

    MessageResponseDto changePassword(ChangePasswordRequestDto requestDto);
}
