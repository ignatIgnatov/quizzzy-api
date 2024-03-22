package com.game.quizzzy.controller;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.GamePlayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-play/room")
@RequiredArgsConstructor
public class GamePlayController {

    private final GamePlayService gamePlayService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{category}")
    public QuestionResponseDto getRandomQuestion(@PathVariable("category") String category) {
        return gamePlayService.getRandomQuestionFromCategory(category);
    }
}
