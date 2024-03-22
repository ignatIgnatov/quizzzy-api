package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.GamePlayService;
import com.game.quizzzy.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GamePlayServiceImpl implements GamePlayService {

    private final RoomService roomService;

    public QuestionResponseDto getRandomQuestionFromCategory(String categoryName) {
        List<QuestionResponseDto> questions = roomService.getAllApprovedQuestionsByCategory(categoryName);

        Random random = new Random();
        int questionNumber = random.nextInt(0, questions.size()) + 1;

        return questions.get(questionNumber);
    }
}
