package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.MessageRequestDto;
import com.game.quizzzy.dto.response.MessageResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.UserRepository;
import com.game.quizzzy.service.MailService;
import com.game.quizzzy.service.UserService;
import com.game.quizzzy.utils.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String SYSTEM_MESSAGE = "System message from Quizzzy";
    private static final String SUBJECT = "no-replay";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;

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
    public MessageResponseDto sendEmailToAllUsers(MessageRequestDto messageRequestDto) {
        for (UserResponseDto user : getAllUsers()) {
            mailService.sendEmail(user.getEmail(), SYSTEM_MESSAGE, messageRequestDto.getMessage());
        }
        return getMessageResponseToAllUsers();
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
            mailService.sendEmail(email, SUBJECT, MessageService.getMessageForDeleteUser(email));
        }
    }

    protected User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private MessageResponseDto getMessageResponseToAllUsers() {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        String message = "Email send to " + getAllUsers().size() + " users.";
        messageResponseDto.setMessage(message);
        return messageResponseDto;
    }
}
