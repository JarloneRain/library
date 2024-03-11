package com.demo.library.service;

import com.demo.library.entity.Book;

import java.util.List;

public interface BookService {
    Book getOne(Integer id);
    List<Book> getAll();
    List<Book> searchTitle(String keyword);
    Integer addBook(String title);
}
