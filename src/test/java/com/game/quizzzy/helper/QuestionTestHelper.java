package com.game.quizzzy.helper;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;

import java.util.List;

public class QuestionTestHelper {

    private static final Long TEST_ID = 1L;
    private static final String TEST_QUESTION = "Question";
    private static final String TEST_TRUE_ANSWER = "True";
    private static final String TEST_WRONG_ANSWER = "Wrong";

    public static Question createApprovedQuestion() {
        return Question.builder()
                .question(TEST_QUESTION)
                .id(TEST_ID)
                .trueAnswer(TEST_TRUE_ANSWER)
                .approved(true)
                .wrongAnswerOne(TEST_WRONG_ANSWER)
                .wrongAnswerTwo(TEST_WRONG_ANSWER)
                .wrongAnswerThree(TEST_WRONG_ANSWER)
                .build();
    }

    public static Room createRoom() {
        return Room.builder()
                .id(TEST_ID)
                .questions(List.of(createApprovedQuestion()))
                .category(Category.USER_QUESTIONS)
                .build();
    }

    public static QuestionRequestDto createQuestionRequestDto() {
        QuestionRequestDto questionRequestDto = new QuestionRequestDto();
        questionRequestDto.setQuestion(TEST_QUESTION);
        questionRequestDto.setTrueAnswer(TEST_TRUE_ANSWER);
        questionRequestDto.setWrongAnswerOne(TEST_WRONG_ANSWER);
        questionRequestDto.setWrongAnswerTwo(TEST_WRONG_ANSWER);
        questionRequestDto.setWrongAnswerThree(TEST_WRONG_ANSWER);
        return questionRequestDto;
    }

    public static QuestionResponseDto createQuestionResponse() {
        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setQuestion(TEST_QUESTION);
        responseDto.setId(TEST_ID);
        responseDto.setTrueAnswer(TEST_TRUE_ANSWER);
        responseDto.setWrongAnswerOne(TEST_WRONG_ANSWER);
        responseDto.setWrongAnswerTwo(TEST_WRONG_ANSWER);
        responseDto.setWrongAnswerThree(TEST_WRONG_ANSWER);
        return responseDto;
    }
}
