package com.game.quizzzy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailRequestDto {
    @Email
    @NotBlank
    private String email;
}
