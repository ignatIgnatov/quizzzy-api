package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Question;

import java.util.List;

public interface QuestionService {
    QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto);

    List<QuestionResponseDto> getAllUserQuestions();

    void deleteQuestion(Long id);

    Question getQuestionById(Long id);

    QuestionResponseDto getQuestionResponseDto(Long id);

    QuestionResponseDto updateQuestion(Long id, QuestionRequestDto requestDto);

    List<QuestionResponseDto> getAllUnapprovedQuestions();
}
