package com.game.quizzzy.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QuestionResponseDto {

    private UserResponseDto user;
    private String question;
    private String trueAnswer;
    private String wrongAnswerOne;
    private String wrongAnswerTwo;
    private String wrongAnswerThree;
    private RoomResponseDto room;
}
