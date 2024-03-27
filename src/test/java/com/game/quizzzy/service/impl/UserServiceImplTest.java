package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.helper.UserTestHelper;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setup() {
        user = UserTestHelper.createUser();
        userResponseDto = UserTestHelper.createUserResponseDto();
    }

    @Test
    void testGetAllUsersSuccessfully() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        List<UserResponseDto> actual = userService.getAllUsers();

        assertEquals(1, actual.size());
        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user, UserResponseDto.class);
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserResponseDto actual = userService.getUser(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(modelMapper, times(1)).map(user, UserResponseDto.class);
    }

    @Test
    void testDeleteUserSuccessfully() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        userService.deleteUser(user.getEmail());

        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).deleteByEmail(user.getEmail());
    }

    @Test
    void testAddPointsToUserSuccessfully() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto actual = userService.addPointsToUser(user.getEmail(), 3L);

        verify(userRepository, times(1)).save(user);
        verify(modelMapper, times(1)).map(user, UserResponseDto.class);
    }
}