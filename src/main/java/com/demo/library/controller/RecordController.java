package com.demo.library.controller;

import com.demo.library.entity.Record;
import com.demo.library.service.RecordService;
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

@Tag(name = "Records")
@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    final RecordService recordService;

    @Operation(summary = "[Admin]Explore all the records.")
    @GetMapping("/")
    public List<Record> getAllRecords() {
        return recordService.findAll();
    }

    @Operation(summary = "Explore all records of user.")
    @GetMapping("/user/{userId}")
    public List<Record> getAllUserRecordsRequest(
            @Parameter(name = "userId")
            @PathVariable
            Integer userId
    ) {
        return recordService.findAllByUserId(userId);
    }

    @Operation(summary = "Explore all records of book.")
    @GetMapping("/book/{bookId}")
    public List<Record> getAllBookRecordsRequest(
            @Parameter(name = "bookId")
            @PathVariable
            Integer bookId
    ) {
        return recordService.findAllByBookId(bookId);
    }

    @Data
    @Schema
    public static class RecoJson {
        Integer userId;
        Integer bookId;
    }

    @Operation(summary = "Borrow a book.", description = "Implemented by POST necessary information.")
    @PostMapping("/borrow")
    public ResponseEntity borrowBookRequest(
            @Parameter(name = "borrowRecordJson")
            @RequestBody
            RecoJson recoJson
    ) {
        try {
            return new ResponseEntity(
                    recordService.borrowBook(recoJson.getUserId(), recoJson.getBookId()),
                    HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    @Schema
    public static class CordJson {
        Integer recoId;
    }

    @Operation(summary = "Return a book.", description = "Implemented by POST recoID.")
    @PostMapping("/return")
    public ResponseEntity returnBookRequest(
            @Parameter(name = "cordJson")
            @RequestBody
            CordJson cordJson
    ) {
        try {
            return new ResponseEntity(
                    recordService.returnBook(cordJson.getRecoId()),
                    HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Renew an order.", description = "Implemented by POST recoID.")
    @PostMapping("/renew")
    public ResponseEntity renewOrderRequest(
            @Parameter(name = "cordJson")
            @RequestBody
            CordJson cordJson
    ) {
        try {
            return new ResponseEntity(
                    recordService.renewOrder(cordJson.getRecoId()),
                    HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Repost lost the book.", description = "Implemented by POST recoID.")
    @PostMapping("/report")
    public ResponseEntity reportLostRequest(
            @Parameter(name = "cordJson")
            @RequestBody
            CordJson cordJson
    ) {
        try {
            return new ResponseEntity(
                    recordService.reportLost(cordJson.getRecoId()),
                    HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
