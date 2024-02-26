package com.demo.library.service;

import com.demo.library.controller.RecordController;
import com.demo.library.domain.Record;

import java.util.List;

public interface RecordService {
    List<Record> findAllByUserId(Long userId);
    List<Record>findAllByBookId(Long bookId);
    List<Record>findAll();
    void borrowBook(RecordController.BorrowRecordJson borrowRecordJson);
    void returnBook(RecordController.ReturnRecordJson returnRecordJson);
}
