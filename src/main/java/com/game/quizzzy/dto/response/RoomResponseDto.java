package com.game.quizzzy.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RoomResponseDto {

    private Long id;
    private String category;
}
