package com.demo.library.controller;

import com.demo.library.entity.Book;
import com.demo.library.service.BookService;
import com.demo.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Books")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookController {
    final BookService bookService;
    final UserService userService;

    @Data
    @Schema
    public static class BookJson {
        String title;
    }

    @Operation(summary = "[Admin]Add a new book.", description = "Implemented by POST necessary information.")
    @PostMapping("/add")
    public ResponseEntity addBookRequest(
            @Parameter(name = "bookJson")
            @RequestBody
            BookJson bookJson,
            @RequestAttribute("ID")
            Integer ID
    ) {
        return userService.getOne(ID).getAdmin()
                ? new ResponseEntity(bookService.addBook(bookJson.getTitle()), HttpStatus.OK)
                : new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Get all the books.")
    @GetMapping("/")
    public List<Book> getAllBooksRequest() {
        return bookService.getAll();
    }

    @Operation(summary = "Get a book.")
    @GetMapping("/{id}")
    public Book getABookRequest(
            @Parameter(name = "id")
            @PathVariable(name = "id")
            Integer id
    ) {
        return bookService.getOne(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search book by the keyword.", description = "Implemented by GET the keyword.")
    public List<Book> searchBookRequest(
            @Parameter(name = "keyword")
            @RequestParam(name = "keyword")
            String keyword
    ) {
        return bookService.searchTitle(keyword);
    }
}
