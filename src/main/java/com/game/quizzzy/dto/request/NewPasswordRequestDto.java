package com.game.quizzzy.dto.request;

import lombok.Data;

@Data
public class NewPasswordRequestDto {
    private String email;
    private String password;
}
