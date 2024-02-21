package com.game.quizzzy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String PWD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final String PWD_PATTERN_VALIDATION_MSG =
            "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long";

    private String firstName;
    private String lastName;

    @NotBlank(message = "Email must not be blank, empty or null")
    @Pattern(regexp = EMAIL_REGEX, message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password must not be blank, empty or null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = PWD_REGEX, message = PWD_PATTERN_VALIDATION_MSG)
    private String password;
}
