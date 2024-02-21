package com.game.quizzzy.service;

import com.game.quizzzy.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);
}
