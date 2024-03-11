package com.demo.library.service.impl;

import com.demo.library.entity.Book;
import com.demo.library.mapper.BookMapper;
import com.demo.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    final BookMapper bookMapper;

    @Override
    public Book getOne(Integer id) {
        List<Book> books = bookMapper.get(id);
        return books.isEmpty() ? null : books.get(0);
    }

    @Override
    public List<Book> getAll() {
        return bookMapper.get(null);
    }

    @Override
    public List<Book> searchTitle(String keyword) {
        return bookMapper.query(keyword);
    }

    @Override
    public Integer addBook(String title) {
        Book book = new Book(null, title, "IL");
        bookMapper.insert(book);
        return book.getId();
    }
}
