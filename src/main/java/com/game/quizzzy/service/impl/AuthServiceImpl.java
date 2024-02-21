package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.LoginRequest;
import com.game.quizzzy.dto.request.UserRequestDto;
import com.game.quizzzy.dto.response.LoginResponse;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.UserAlreadyExistsException;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.Role;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.RoleRepository;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.security.jwt.JwtUtils;
import com.game.quizzzy.security.user.ApiUserDetails;
import com.game.quizzzy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        throwExceptionWhenUserAlreadyExists(userRequestDto);
        User user = createUser(userRequestDto);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = getUserByEmail(loginRequest);
        Authentication authentication = getAuthentication(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ApiUserDetails userDetails = (ApiUserDetails) authentication.getPrincipal();

        return LoginResponse.builder()
                .id(user.getId())
                .email(loginRequest.getEmail())
                .token(getToken(authentication))
                .roles(getRoles(userDetails))
                .build();
    }

    @Override
    public LoginResponse logout() {
        return new LoginResponse();
    }

    private String getToken(Authentication authentication) {
        return jwtUtils.generateJwtTokenForUser(authentication);
    }

    private User getUserByEmail(LoginRequest loginRequest) {
        return userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));
    }

    private Authentication getAuthentication(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));
    }

    private List<String> getRoles(ApiUserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    private User createUser(UserRequestDto userRequestDto) {
        createRolesInDatabase();
        return User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .roles(Collections.singletonList(selectRole()))
                .build();
    }

    private Role selectRole() {
        Role userRole = getRole(ROLE_USER);
        if (userRepository.count() == 0) {
            userRole = getRole(ROLE_ADMIN);
        }
        return userRole;
    }

    private Role getRole(String role) {
        return roleRepository.findByName(role).get();
    }

    private void createRolesInDatabase() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(ROLE_ADMIN);

            Role userRole = new Role();
            userRole.setName(ROLE_USER);
            roleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    private void throwExceptionWhenUserAlreadyExists(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException(userRequestDto.getEmail());
        }
    }
}
