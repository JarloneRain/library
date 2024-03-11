package com.demo.library.mapper;

import com.demo.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {
    List<Book> get(Integer id);

    List<Book> query(String keyword);

    Integer insert(Book book);

    Integer update(Book book);
}
