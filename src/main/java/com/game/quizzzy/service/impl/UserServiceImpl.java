package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    @Override
    public UserResponseDto getUser(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        UserResponseDto user = getUser(email);
        if (user != null) {
            userRepository.deleteByEmail(email);
        }
    }
}
