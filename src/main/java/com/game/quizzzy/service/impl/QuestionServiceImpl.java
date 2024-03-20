package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.QuestionRepository;
import com.game.quizzzy.repository.RoomRepository;
import com.game.quizzzy.service.AuthService;
import com.game.quizzzy.service.QuestionService;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final RoomRepository roomRepository;
    private final AuthService authService;


    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) {

        UserResponseDto user = userService.getUser(questionRequestDto.getEmail());
        UserResponseDto currentUser = authService.getCurrentUser();
        if (!user.getEmail().equals(currentUser.getEmail())) {
            throw new UserNotFoundException(user.getEmail());
        }

        Question question = modelMapper.map(questionRequestDto, Question.class);
        question.setRoom(getRoom());
        question.setAuthor(modelMapper.map(user, User.class));
        questionRepository.save(question);

        return modelMapper.map(question, QuestionResponseDto.class);
    }

    private Room getRoom() {
        Room room = roomRepository.findByCategory(Category.USER_QUESTIONS);
        if (room == null) {
            return roomRepository.save(Room.builder().category(Category.USER_QUESTIONS).build());
        }
        return room;
    }

    public List<QuestionResponseDto> getAllUserQuestions() {
        return questionRepository.findAllByRoomCategory(Category.USER_QUESTIONS).stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .toList();
    }
}
