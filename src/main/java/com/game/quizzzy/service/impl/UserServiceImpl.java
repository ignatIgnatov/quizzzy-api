package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.service.MailService;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public MessageResponseDto sendForgotPassword(String email) {
        User user = findByEmail(email);
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Email send successfully!");
        String text = "Hello " + email + ", \n" +
                "\n" +
                "You are receiving this message because you have requested to change your password. \n" +
                "Follow the instructions to change your password. \n" +
                "\n" +
                "Click on this link to add your new password: \n" +
                "http://localhost:3000/forgot-password \n" +
                "\n" +
                "\n" +
                "\n" +
                "This is a system message. Please don't answer it! \n" +
                "\n" +
                "Best regards! \n" +
                "Quizzzy team";
        mailService.sendEmail(user.getEmail(), "no-replay", text);
        return responseDto;
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
