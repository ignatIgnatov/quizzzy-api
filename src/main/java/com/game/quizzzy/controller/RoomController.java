package com.game.quizzzy.controller;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/game/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{category}")
    public List<QuestionResponseDto> getAllApprovedQuestionsByCategory(@PathVariable("category") String category) {
        return roomService.getAllApprovedQuestionsByCategory(category);
    }
}
