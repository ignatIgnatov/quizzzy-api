package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        User user = findByEmail(email);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto addPointsToUser(String email, Long points) {
        User user = findByEmail(email);
        user.setPoints(points);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    public List<UserResponseDto> getAllUsersOrderedByPoints() {
        return userRepository.findAllByOrderByPointsDesc().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
        }
    }

    protected User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
