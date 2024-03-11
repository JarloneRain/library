package com.demo.library.mapper;

import com.demo.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> get(Integer id);

    List<User> query(String keyword);

    Integer insert(User user);

    Integer update(User user);
}
