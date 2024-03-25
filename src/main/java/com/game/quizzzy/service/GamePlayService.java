package com.game.quizzzy.service;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;

public interface GamePlayService {
    QuestionResponseDto getRandomQuestionFromCategory(String categoryName);
}
