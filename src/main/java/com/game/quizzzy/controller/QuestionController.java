package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.UserQuestionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class QuestionController {

    private final UserQuestionsService userQuestionsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public QuestionResponseDto createQuestion(@RequestBody @Valid QuestionRequestDto questionRequestDto) {
        return userQuestionsService.createQuestion(questionRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<QuestionResponseDto> getAllUserQuestions() {
        return userQuestionsService.getAllUserQuestions();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        userQuestionsService.deleteQuestion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public QuestionResponseDto getUserQuestion(@PathVariable("id") Long id) {
        return userQuestionsService.getUserQuestion(id);
    }
}
