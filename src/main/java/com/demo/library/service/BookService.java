package com.demo.library.service;

import com.demo.library.controller.BookController;
import com.demo.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book>findAll();
    List<Book>findAllByTitleContaining(String keyword);
    void addBook(BookController.BookJson bookJson);
    void borrowBook(Long id);
    void returnBook(Long id);
}
