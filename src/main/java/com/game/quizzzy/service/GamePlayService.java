package com.game.quizzzy.service;

import com.game.quizzzy.dto.response.QuestionResponseDto;

public interface GamePlayService {
    QuestionResponseDto getRandomQuestionFromCategory(String categoryName);
}
