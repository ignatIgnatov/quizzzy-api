package com.game.quizzzy.helper;

import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.model.Role;
import com.game.quizzzy.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Collections;

public class UserTestHelper {
    public static final Long USER_ID = 1L;
    public static final String FIRST_NAME = "TestUserName";
    public static final String LAST_NAME = "TestUserLastName";
    public static final String USER_EMAIL = "test@example.com";
    public static final String USER_PASSWORD = "P@ssw0rd";
    public static final Long USER_POINTS = 0L;

    public static User createUser() {
        return User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .points(USER_POINTS)
                .roles(Collections.singletonList(createRole()))
                .build();
    }

    public static UserRequestDto createUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(FIRST_NAME);
        userRequestDto.setLastName(LAST_NAME);
        userRequestDto.setEmail(USER_EMAIL);
        userRequestDto.setPassword(USER_PASSWORD);
        return userRequestDto;
    }

    public static Role createRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        return role;
    }

    public static LoginRequest createLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(USER_EMAIL);
        loginRequest.setPassword(USER_PASSWORD);
        return loginRequest;
    }

    public static UserResponseDto createUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(USER_EMAIL);
        userResponseDto.setId(USER_ID);
        userResponseDto.setFirstName(FIRST_NAME);
        userResponseDto.setLastName(LAST_NAME);
        userResponseDto.setPoints(USER_POINTS);
        return userResponseDto;
    }
}
