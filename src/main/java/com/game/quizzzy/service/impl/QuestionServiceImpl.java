package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.QuestionNotFoundException;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.QuestionRepository;
import com.game.quizzzy.service.AuthService;
import com.game.quizzzy.service.QuestionService;
import com.game.quizzzy.service.RoomService;
import com.game.quizzzy.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;
    private final RoomService roomService;
    private final AuthService authService;
    private final UserService userService;


    @Transactional
    public QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto) {

        UserResponseDto currentUser = authService.getCurrentUser();

        Question question = modelMapper.map(questionRequestDto, Question.class);
        question.setRoom(roomService.getRoom(Category.USER_QUESTIONS));
        question.setAuthor(modelMapper.map(currentUser, User.class));
        questionRepository.save(question);

        return modelMapper.map(question, QuestionResponseDto.class);
    }

    @Override
    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto requestDto) {
        Question question = getQuestionById(id);
        UserResponseDto author = userService.getUser(authService.getCurrentUser().getEmail());
        question.setAuthor(modelMapper.map(author, User.class));
        question.setQuestion(requestDto.getQuestion());
        question.setTrueAnswer(requestDto.getTrueAnswer());
        question.setWrongAnswerOne(requestDto.getWrongAnswerOne());
        question.setWrongAnswerTwo(requestDto.getWrongAnswerTwo());
        question.setWrongAnswerThree(requestDto.getWrongAnswerThree());
        question.setApproved(true);

        questionRepository.save(question);
        return modelMapper.map(question, QuestionResponseDto.class);
    }

    public List<QuestionResponseDto> getAllUserQuestions() {
        return questionRepository.findAllByRoomCategory(Category.USER_QUESTIONS).stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .toList();
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        if (question != null) {
            questionRepository.deleteById(id);
        }
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException(id)
        );
    }

    @Override
    public QuestionResponseDto getQuestionResponseDto(Long id) {
        Question question = getQuestionById(id);
        return modelMapper.map(question, QuestionResponseDto.class);
    }
}
