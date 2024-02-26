package com.demo.library.service.impl;

import com.demo.library.controller.RecordController;
import com.demo.library.domain.Record;
import com.demo.library.domain.RecordRepository;
import com.demo.library.service.BookService;
import com.demo.library.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    BookService bookService;

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public List<Record> findAllByUserId(Long userId) {
        return recordRepository.findAllByUserId(userId);
    }

    @Override
    public List<Record> findAllByBookId(Long bookId) {
        return recordRepository.findAllByBookId(bookId);
    }

    @Override
    public void borrowBook(RecordController.BorrowRecordJson borrowRecordJson) {
        Record record = new Record(
                borrowRecordJson.getBorrowTime(),
                borrowRecordJson.getUserId(),
                borrowRecordJson.getBookId()
        );
        bookService.borrowBook(record.getBookId());
        recordRepository.save(record);
    }

    @Override
    public void returnBook(RecordController.ReturnRecordJson returnRecordJson) {
        recordRepository
                .findById(returnRecordJson.getRecordId())
                .ifPresentOrElse(record -> {
                    bookService.returnBook(record.getBookId());
                    record.setReturnTime(returnRecordJson.getReturnTime());
                    recordRepository.save(record);
                }, () -> {
                    throw new RuntimeException("The record id{" + returnRecordJson.getRecordId() + "} is not exist.");
                });
    }
}
