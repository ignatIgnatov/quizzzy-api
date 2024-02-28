package com.game.quizzzy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QuestionRequestDto {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Pattern(regexp = EMAIL_REGEX, message = "")
    private String email;

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
