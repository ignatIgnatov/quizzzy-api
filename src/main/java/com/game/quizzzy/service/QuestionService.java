package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;

import java.util.List;

public interface QuestionService {
    QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto);

    List<QuestionResponseDto> getAllUserQuestions();

    void deleteQuestion(Long id);
}
