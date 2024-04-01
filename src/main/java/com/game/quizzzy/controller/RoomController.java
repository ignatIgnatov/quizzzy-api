package com.game.quizzzy.controller;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Get all approved user questions")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{category}")
    public List<QuestionResponseDto> getAllApprovedQuestionsByCategory(@PathVariable("category") String category) {
        return roomService.getAllApprovedQuestionsByCategory(category);
    }
}
