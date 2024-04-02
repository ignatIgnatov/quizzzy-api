package com.game.quizzzy.controller;

import com.game.quizzzy.dto.request.ChangePasswordRequestDto;
import com.game.quizzzy.dto.response.UserResponseDto;
import com.game.quizzzy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all registered users")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserByEmail(@PathVariable("email") String email) {
        return userService.getUser(email);
    }

    @Operation(summary = "Delete user by email")
    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
    public void deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
    }

    @Operation(summary = "Add points to user")
    @PutMapping("/{email}/{points}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto addPointsToUser(@Valid @PathVariable("email") String email, @Valid @PathVariable("points") Long points) {
        return userService.addPointsToUser(email, points);
    }
}
