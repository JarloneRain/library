package com.demo.library.service;

import com.demo.library.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> findAllByUserId(Integer userId);
    List<Record>findAllByBookId(Integer bookId);
    List<Record>findAll();
    Integer borrowBook(Integer userId, Integer bookId);
    Integer returnBook(Integer recoId);
    Integer renewOrder(Integer recoId);
    Integer reportLost(Integer recoId);
}
