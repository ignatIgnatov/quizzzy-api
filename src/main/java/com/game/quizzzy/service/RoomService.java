package com.game.quizzzy.service;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Room;

import java.util.List;

public interface RoomService {
    Room getRoom(Category category);

    List<QuestionResponseDto> getAllApprovedQuestionsByCategory(String category);
}
