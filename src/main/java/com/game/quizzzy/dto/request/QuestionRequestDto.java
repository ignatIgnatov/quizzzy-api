package com.game.quizzzy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionRequestDto {

    @NotBlank(message = "Question must not be empty")
    private String question;

    @NotBlank(message = "True Answer must not be empty")
    private String trueAnswer;

    @NotBlank(message = "Wrong answer one must not be empty")
    private String wrongAnswerOne;

    @NotBlank(message = "Wrong answer two must not be empty")
    private String wrongAnswerTwo;

    @NotBlank(message = "Wrong answer three must not be empty")
    private String wrongAnswerThree;
}
