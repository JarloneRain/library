package com.demo.library.controller;

import com.demo.library.domain.Book;
import com.demo.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Books")
@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    BookService bookService;

    @Data
    @Schema
    public static class BookJson {
        String title;
    }

    @Operation(summary = "Add a new book.", description = "Implemented by POST necessary information.")
    @PostMapping("/")
    public String addBookRequest(
            @Parameter(name = "bookJson")
            @RequestBody
            BookJson bookJson
    ) {
        bookService.addBook(bookJson);
        return "success";
    }

    @Operation(summary = "Get all the books.")
    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/search")
    @Operation(summary = "Search book by the keyword.", description = "Implemented by GET the keyword.")
    public List<Book> searchBookRequest(
            @Parameter(name = "keyword")
            @RequestParam(name = "keyword")
            String keyword
    ) {
        return bookService.findAllByTitleContaining(keyword);
    }
}
