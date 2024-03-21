package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.QuestionNotFoundException;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.RoomRepository;
import com.game.quizzzy.repository.UserQuestionsRepository;
import com.game.quizzzy.service.AuthService;
import com.game.quizzzy.service.UserQuestionsService;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuestionsServiceImpl implements UserQuestionsService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserQuestionsRepository userQuestionsRepository;
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
        userQuestionsRepository.save(question);

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
        return userQuestionsRepository.findAllByRoomCategory(Category.USER_QUESTIONS).stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .toList();
    }

    public void deleteQuestion(Long id) {
        Question question = userQuestionsRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        if (question != null) {
            userQuestionsRepository.deleteById(id);
        }
    }

    @Override
    public Question getQuestion(Long id) {
        return userQuestionsRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException(id)
        );
    }

    @Override
    public QuestionResponseDto getUserQuestion(Long id) {
        Question question = getQuestion(id);
        return modelMapper.map(question, QuestionResponseDto.class);
    }
}
