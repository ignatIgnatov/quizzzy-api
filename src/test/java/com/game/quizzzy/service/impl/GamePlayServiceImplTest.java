package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.helper.QuestionTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamePlayServiceImplTest {

    private static final String USER_QUESTION_CATEGORY = "USER_QUESTIONS";

    @InjectMocks
    private GamePlayServiceImpl gamePlayService;
    @Mock
    private RoomServiceImpl roomService;

    private QuestionResponseDto firstTestQuestion;
    private QuestionResponseDto secondTestQuestion;
    private QuestionResponseDto thirdTestQuestion;

    @BeforeEach
    void setUp() {
        firstTestQuestion = QuestionTestHelper.createQuestionResponse();
        secondTestQuestion = QuestionTestHelper.createQuestionResponse();
        thirdTestQuestion = QuestionTestHelper.createQuestionResponse();
    }


    @Test
    void testGetRandomQuestion() {
        when(roomService.getAllApprovedQuestionsByCategory(USER_QUESTION_CATEGORY))
                .thenReturn(List.of(firstTestQuestion, secondTestQuestion, thirdTestQuestion));

        QuestionResponseDto actual = gamePlayService.getRandomQuestionFromCategory(USER_QUESTION_CATEGORY);

        assertNotNull(actual);
        verify(roomService, times(1)).getAllApprovedQuestionsByCategory(USER_QUESTION_CATEGORY);
    }
}