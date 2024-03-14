package com.demo.library.service;

import com.demo.library.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getOne(Integer id);

    List<User> searchName(String keyword);

    Integer modify(Integer id, String name, String password);
    Integer register(String name, String password);

    boolean login(Integer id, String password);

}
