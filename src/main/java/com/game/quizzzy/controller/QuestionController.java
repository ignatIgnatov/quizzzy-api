package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create user question")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDto createQuestion(@RequestBody @Valid QuestionRequestDto questionRequestDto) {
        return questionService.createQuestion(questionRequestDto);
    }

    @Operation(summary = "Get all user questions")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponseDto> getAllUserQuestions() {
        return questionService.getAllUserQuestions();
    }

    @Operation(summary = "Delete user question")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
    }

    @Operation(summary = "Get user question by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto getUserQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestionResponseDto(id);
    }

    @Operation(summary = "Approve user question")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponseDto approveUserQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequestDto requestDto) {
        return questionService.updateQuestion(id, requestDto);
    }

    @Operation(summary = "Get all unapproved user questions")
    @GetMapping("/unapproved")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionResponseDto> getAllUnapprovedQuestions() {
        return questionService.getAllUnapprovedQuestions();
    }
}
