package com.game.quizzzy.service;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;

public interface QuestionService {
    QuestionResponseDto createQuestion(QuestionRequestDto questionRequestDto);
}
