package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public QuestionResponseDto createQuestion(@RequestBody @Valid QuestionRequestDto questionRequestDto) {
        return questionService.createQuestion(questionRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<QuestionResponseDto> getAllUserQuestions() {
        return questionService.getAllUserQuestions();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public QuestionResponseDto getUserQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestionResponseDto(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public QuestionResponseDto approveUserQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequestDto requestDto) {
        return questionService.updateQuestion(id, requestDto);
    }
}
