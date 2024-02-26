package com.demo.library.service.impl;

import com.demo.library.controller.BookController;
import com.demo.library.domain.Book;
import com.demo.library.domain.BookRepository;
import com.demo.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    @Override
    public void addBook(BookController.BookJson bookJson) {
        bookRepository.save(new Book(bookJson.getTitle(), false));
    }

    @Override
    public void borrowBook(Long id) {
        bookRepository.findById(id).ifPresentOrElse(
                book -> {
                    book.setLent(false);
                    bookRepository.save(book);
                }, () -> {
                    throw new RuntimeException("The book id{" + id + "} is not exist.");
                }
        );
    }

    @Override
    public void returnBook(Long id) {
        bookRepository.findById(id).ifPresentOrElse(
                book -> {
                    book.setLent(false);
                    bookRepository.save(book);
                }, () -> {
                    //Log
                });
    }
}
