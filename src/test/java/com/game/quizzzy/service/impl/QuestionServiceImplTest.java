package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.request.QuestionRequestDto;
import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.exception.QuestionNotFoundException;
import com.game.quizzzy.exception.UserNotFoundException;
import com.game.quizzzy.helper.QuestionTestHelper;
import com.game.quizzzy.helper.UserTestHelper;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.User;
import com.game.quizzzy.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    private static final String USER_QUESTION_CATEGORY = "USER_QUESTIONS";

    @InjectMocks
    private QuestionServiceImpl questionService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private RoomServiceImpl roomService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private AuthServiceImpl authService;

    private UserResponseDto currentUser;
    private QuestionRequestDto questionRequestDto;
    private Question question;
    private UserResponseDto user;

    @BeforeEach
    void setUp() {
        currentUser = UserTestHelper.createUserResponseDto();
        questionRequestDto = QuestionTestHelper.createQuestionRequestDto();
        question = QuestionTestHelper.createApprovedQuestion();
        user = UserTestHelper.createUserResponseDto();
    }

    @Test
    void testCreateQuestionSuccessfully() {
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(modelMapper.map(questionRequestDto, Question.class)).thenReturn(question);

        QuestionResponseDto actual = questionService.createQuestion(questionRequestDto);

        verify(questionRepository, times(1)).save(question);
        verify(modelMapper, times(1)).map(questionRequestDto, Question.class);
        verify(modelMapper, times(1)).map(question, QuestionResponseDto.class);
    }

    @Test
    void testUpdateQuestionShouldThrowWhenQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenThrow(QuestionNotFoundException.class);

        assertThrows(QuestionNotFoundException.class,
                () -> questionService.updateQuestion(question.getId(), questionRequestDto));
    }

    @Test
    void testUpdateQuestionShouldThrowWhenUserNotFound() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(authService.getCurrentUser()).thenReturn(user);
        when(userService.getUser(user.getEmail())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> questionService.updateQuestion(question.getId(), questionRequestDto));
    }

    @Test
    void testUpdateQuestionSuccessfully() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(authService.getCurrentUser()).thenReturn(user);
        when(userService.getUser(user.getEmail())).thenReturn(user);

        QuestionResponseDto actual = questionService.updateQuestion(question.getId(), questionRequestDto);

        verify(questionRepository, times(1)).findById(question.getId());
        verify(authService, times(1)).getCurrentUser();
        verify(userService, times(1)).getUser(user.getEmail());
        verify(questionRepository, times(1)).save(question);
        verify(modelMapper, times(1)).map(user, User.class);
        verify(modelMapper, times(1)).map(question, QuestionResponseDto.class);
    }

    @Test
    void testGetQuestionShouldThrowWhenQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenThrow(QuestionNotFoundException.class);

        assertThrows(QuestionNotFoundException.class,
                () -> questionService.getQuestionById(question.getId()));
    }

    @Test
    void testGetQuestionSuccessfully() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        Question actual = questionService.getQuestionById(question.getId());

        verify(questionRepository, times(1)).findById(question.getId());
    }

    @Test
    void testGetAllUserQuestionsSuccessfully() {
        Category category = Category.valueOf(USER_QUESTION_CATEGORY);
        when(questionRepository.findAllByRoomCategory(category)).thenReturn(List.of(question));

        List<QuestionResponseDto> actual = questionService.getAllUserQuestions();

        assertEquals(1, actual.size());
        verify(questionRepository, times(1)).findAllByRoomCategory(category);
        verify(modelMapper, times(1)).map(question, QuestionResponseDto.class);
    }

    @Test
    void testDeleteQuestionSuccessfully() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        questionService.deleteQuestion(question.getId());

        verify(questionRepository, times(1)).findById(question.getId());
        verify(questionRepository, times(1)).deleteById(question.getId());
    }

    @Test
    void testDeleteQuestionShouldThrowWhenQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenThrow(QuestionNotFoundException.class);

        assertThrows(QuestionNotFoundException.class,
                () -> questionService.deleteQuestion(question.getId()));
    }

    @Test
    void testGetQuestionResponseSuccessfully() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        QuestionResponseDto questionResponseDto = QuestionTestHelper.createQuestionResponse();
        when(modelMapper.map(question, QuestionResponseDto.class)).thenReturn(questionResponseDto);

        QuestionResponseDto actual = questionService.getQuestionResponseDto(question.getId());

        assertEquals(questionResponseDto.getQuestion(), actual.getQuestion());
    }

}