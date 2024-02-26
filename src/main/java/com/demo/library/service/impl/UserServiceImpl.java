package com.demo.library.service.impl;

import com.demo.library.controller.UserController;
import com.demo.library.domain.User;
import com.demo.library.domain.UserRepository;
import com.demo.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByNameContaining(keyword);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void register(UserController.UserJson userJson) {
        userRepository.save(new User(userJson.getName()));
    }
}
