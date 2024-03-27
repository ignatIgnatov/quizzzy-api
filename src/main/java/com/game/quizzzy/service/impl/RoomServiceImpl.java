package com.game.quizzzy.service.impl;

import com.game.quizzzy.dto.response.QuestionResponseDto;
import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Room;
import com.game.quizzzy.repository.QuestionRepository;
import com.game.quizzzy.repository.RoomRepository;
import com.game.quizzzy.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final QuestionRepository questionRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<QuestionResponseDto> getAllApprovedQuestionsByCategory(String categoryName) {
        Category category = Category.valueOf(categoryName);
        return questionRepository.findAllApprovedQuestionsByRoom(getRoom(category))
                .stream()
                .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                .toList();
    }

    @Override
    public Room getRoom(Category category) {
        Room room = roomRepository.findByCategory(category);
        if (room == null) {
            return roomRepository.save(Room.builder().category(category).build());
        }
        return room;
    }
}
