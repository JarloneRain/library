package com.demo.library.service;

import com.demo.library.controller.UserController;
import com.demo.library.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> searchUsers(String keyword);

    void register(UserController.UserJson userJson);

}
