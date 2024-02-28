package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.QuestionRepository;
import com.game.quizzzy.repository.RoomRepository;
import com.game.quizzzy.service.QuestionService;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final RoomRepository roomRepository;

    public QuestionServiceImpl(
            UserService userService,
            ModelMapper modelMapper,
            QuestionRepository questionRepository,
            RoomRepository roomRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) {

        User user = userService.getUser(questionRequestDto.getEmail());

        Question question = modelMapper.map(questionRequestDto, Question.class);
        question.setRoom(getRoom());
        question.setAuthor(user);
        questionRepository.save(question);

        QuestionResponseDto questionResponseDto = modelMapper.map(question, QuestionResponseDto.class);
        questionResponseDto.setUser(modelMapper.map(user, UserResponseDto.class));
        return questionResponseDto;
    }

    private Room getRoom() {
        Room room = roomRepository.findByCategory(Category.USER_QUESTIONS);
        if (room == null) {
            return roomRepository.save(Room.builder().category(Category.USER_QUESTIONS).build());
        }
        return room;
    }
}
