package com.demo.library.service.impl;

import com.demo.library.entity.User;
import com.demo.library.mapper.UserMapper;
import com.demo.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.get(null);
    }

    @Override
    public User getOne(Integer id) {
        List<User> users = userMapper.get(id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> searchName(String keyword) {
        return userMapper.query("%" + keyword + "%");
    }

    @Override
    public Integer modify(Integer id, String name, String password) {
        User old = userMapper.get(id).get(0);
        return userMapper.update(new User(id, false,
                name == null ? old.getName() : name,
                password == null ? old.getPassword() : password));
    }

    @Override
    public Integer register(String name, String password) {
        User user = new User(null, false, name, password);
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public boolean login(Integer id, String password) {
        return userMapper.get(id).get(0).getPassword().equals(password);
    }
}
