package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.MessageRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    void deleteUser(String email);

    UserResponseDto getUser(String email);

    UserResponseDto addPointsToUser(String email, Long points);

    List<UserResponseDto> getAllUsersOrderedByPoints();

    MessageResponseDto sendEmailToAllUsers(MessageRequestDto messageRequestDto);

}
