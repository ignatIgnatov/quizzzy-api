package com.game.quizzzy.controller;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.GamePlayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-play/room")
@RequiredArgsConstructor
public class GamePlayController {

    private final GamePlayService gamePlayService;

    @Operation(summary = "Get random question from category")
    @GetMapping("/{category}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto getRandomQuestion(@PathVariable("category") String category) {
        return gamePlayService.getRandomQuestionFromCategory(category);
    }
}
