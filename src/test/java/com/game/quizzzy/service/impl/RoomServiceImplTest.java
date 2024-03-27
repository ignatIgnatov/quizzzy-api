package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.helper.QuestionTestHelper;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;
import com.game.quizzzy.repository.QuestionRepository;
import com.game.quizzzy.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    private static final String USER_QUESTION_CATEGORY = "USER_QUESTIONS";

    @InjectMocks
    private RoomServiceImpl roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ModelMapper modelMapper;

    private Question question;
    private Room room;

    @BeforeEach
    void setUp() {
        question = QuestionTestHelper.createApprovedQuestion();
        room = QuestionTestHelper.createRoom();
    }

    @Test
    void testGetAllApprovedQuestionSuccessfully() {
        Category category = Category.valueOf(USER_QUESTION_CATEGORY);
        when(roomRepository.findByCategory(category)).thenReturn(room);
        when(questionRepository.findAllApprovedQuestionsByRoom(room)).thenReturn(List.of(question));

        List<QuestionResponseDto> actual = roomService.getAllApprovedQuestionsByCategory(USER_QUESTION_CATEGORY);

        verify(questionRepository, times(1)).findAllApprovedQuestionsByRoom(room);
    }

    @Test
    void testGetRoomSuccessfullyWhenRoomExists() {
        Category category = Category.valueOf(USER_QUESTION_CATEGORY);
        when(roomRepository.findByCategory(category)).thenReturn(room);

        Room actual = roomService.getRoom(category);

        verify(roomRepository, times(1)).findByCategory(category);
        verify(roomRepository, times(0)).save(room);
    }

    @Test
    void testGetRoomSuccessfullyWhenRoomNotExists() {
        Category category = Category.valueOf(USER_QUESTION_CATEGORY);
        when(roomRepository.findByCategory(category)).thenReturn(null);

        Room actual = roomService.getRoom(category);

        verify(roomRepository, times(1)).findByCategory(category);
        verify(roomRepository, times(1)).save(any());
    }
}