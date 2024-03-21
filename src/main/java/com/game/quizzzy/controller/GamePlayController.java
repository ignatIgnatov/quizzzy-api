package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.GamePlayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GamePlayController {

    private final GamePlayService gamePlayService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public QuestionResponseDto approveUserQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequestDto requestDto) {
        return gamePlayService.saveUserQuestionToGamePlay(id, requestDto);
    }
}
