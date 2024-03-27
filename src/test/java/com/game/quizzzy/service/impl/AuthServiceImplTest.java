package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.LoginResponse;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.NoAuthenticatedUserException;
import com.game.quizzzy.exception.UserAlreadyExistsException;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.helper.UserTestHelper;
import com.game.quizzzy.model.Role;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.RoleRepository;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.security.jwt.JwtUtils;
import com.game.quizzzy.security.user.ApiUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private static final String TEST_TOKEN = "token";
    private static final String TEST_ENCODED_PASSWORD = "$2a$10$WIgDfys.uGK53Q3V13l8AOYCH7M1cVHulX8klIq0PLB/KweY/Onhi";

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private Authentication authentication;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ApiUserDetails userDetails;

    private UserRequestDto userRequestDto;
    private User user;
    private Role role;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = UserTestHelper.createUser();
        userRequestDto = UserTestHelper.createUserRequestDto();
        loginRequest = UserTestHelper.createLoginRequest();
        role = UserTestHelper.createRole();
    }

    @Test
    void testRegisterUserSuccessfully() {
        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(any()))
                .thenReturn(TEST_ENCODED_PASSWORD);

        UserResponseDto actual = authService.registerUser(userRequestDto);

        verify(roleRepository, times(1)).count();
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserShouldThrowWhenEmailExists() {
        when(userRepository.existsByEmail(userRequestDto.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> authService.registerUser(userRequestDto));
    }

    @Test
    void testLoginSuccessfullyReturnJwtToken() {
        userDetails = mock(ApiUserDetails.class);
        authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtils.generateJwtTokenForUser(authentication)).thenReturn(TEST_TOKEN);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        LoginResponse actual = authService.login(loginRequest);

        assertEquals(TEST_TOKEN, actual.getToken());
        verify(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        verify(userRepository).findByEmail(user.getEmail());
        verify(jwtUtils).generateJwtTokenForUser(authentication);
    }

    @Test
    void testLoginShouldThrowWhenUserNotFound() {
        when(userRepository.findByEmail(userRequestDto.getEmail())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> authService.login(loginRequest));
    }

    @Test
    void testLogoutShouldReturnNewEmptyLoginResponse() {

        LoginResponse actual = authService.logout();

        assertNull(actual.getToken());
        assertNull(actual.getId());
        assertNull(actual.getEmail());
        assertNull(actual.getRoles());
    }

    @Test
    void testGetCurrentUserSuccessfully() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserResponseDto userResponseDto = UserTestHelper.createUserResponseDto();
        when(modelMapper.map(authentication.getPrincipal(), UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto actual = authService.getCurrentUser();

        assertEquals(actual.getEmail(), user.getEmail());
        assertEquals(actual.getId(), user.getId());
    }

    @Test
    void testGetCurrentUserShouldThrowWhenNoAuthentication() {
        authentication = null;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertThrows(NoAuthenticatedUserException.class, () -> authService.getCurrentUser());
    }
}