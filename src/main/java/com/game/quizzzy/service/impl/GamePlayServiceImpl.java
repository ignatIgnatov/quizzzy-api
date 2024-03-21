package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.GamePlayRepository;
import com.game.quizzzy.repository.UserQuestionsRepository;
import com.game.quizzzy.service.GamePlayService;
import com.game.quizzzy.service.UserQuestionsService;
import com.game.quizzzy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamePlayServiceImpl implements GamePlayService {

    private final ModelMapper modelMapper;
    private final UserQuestionsService userQuestionsService;
    private final GamePlayRepository gamePlayRepository;
    private final UserService userService;

    @Override
    public QuestionResponseDto saveUserQuestionToGamePlay(Long id, QuestionRequestDto requestDto) {
        Question question = userQuestionsService.getQuestion(id);
        UserResponseDto author = userService.getUser(requestDto.getEmail());
        question.setAuthor(modelMapper.map(author, User.class));
        question.setQuestion(requestDto.getQuestion());
        question.setTrueAnswer(requestDto.getTrueAnswer());
        question.setWrongAnswerOne(requestDto.getWrongAnswerOne());
        question.setWrongAnswerTwo(requestDto.getWrongAnswerTwo());
        question.setWrongAnswerThree(requestDto.getWrongAnswerThree());

        gamePlayRepository.save(question);
        userQuestionsService.deleteQuestion(id);
        return modelMapper.map(question, QuestionResponseDto.class);
    }
}
