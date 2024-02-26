package com.demo.library.controller;

import com.demo.library.domain.Record;
import com.demo.library.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Tag(name = "Records")
@RestController
@RequestMapping("/records")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Operation(summary = "Explore all the records.")
    @GetMapping("/")
    public List<Record> getAllRecords() {
        return recordService.findAll();
    }

    @Operation(summary = "Explore all records of user.")
    @GetMapping("/user/{userId}")
    public List<Record> getAllUserRecordsRequest(
            @Parameter(name = "userId")
            @PathVariable
            Long userId
    ) {
        return recordService.findAllByUserId(userId);
    }

    @Operation(summary = "Explore all records of book.")
    @GetMapping("/book/{bookId}")
    public List<Record> getAllBookRecordsRequest(
            @Parameter(name = "bookId")
            @PathVariable
            Long bookId
    ) {
        return recordService.findAllByBookId(bookId);
    }

    @Data
    @Schema
    public static class BorrowRecordJson {
        Timestamp borrowTime;
        Long userId;
        Long bookId;
    }

    @Operation(summary = "Borrow a book.", description = "Implemented by POST necessary information.")
    @PostMapping("/")
    public String borrowRequest(
            @Parameter(name = "borrowRecordJson")
            @RequestBody
            BorrowRecordJson borrowRecordJson
    ) {
        recordService.borrowBook(borrowRecordJson);
        return "success";
    }

    @Data
    @Schema
    public static class ReturnRecordJson {
        Long recordId;
        Timestamp returnTime;
    }

    @Operation(summary = "Return a book.", description = "Implemented by PUT necessary information.")
    @PutMapping("/")
    public String returnRequest(
            @Parameter(name = "returnRecordJson")
            @RequestBody
            ReturnRecordJson returnRecordJson
    ) {
        recordService.returnBook(returnRecordJson);
        return "success";
    }


}
