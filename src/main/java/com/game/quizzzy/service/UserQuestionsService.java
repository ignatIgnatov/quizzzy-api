package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Question;

import java.util.List;

public interface UserQuestionsService {
    QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto);

    List<QuestionResponseDto> getAllUserQuestions();

    void deleteQuestion(Long id);

    Question getQuestion(Long id);

    QuestionResponseDto getUserQuestion(Long id);
}
