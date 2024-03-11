package com.demo.library.service.impl;

import com.demo.library.entity.Book;
import com.demo.library.entity.Cord;
import com.demo.library.entity.Reco;
import com.demo.library.entity.Record;
import com.demo.library.mapper.BookMapper;
import com.demo.library.mapper.RecordMapper;
import com.demo.library.mapper.ToolMapper;
import com.demo.library.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {
    final RecordMapper recordMapper;
    final BookMapper bookMapper;
    final ToolMapper toolMapper;

    @Override
    public List<Record> findAllByUserId(Integer userId) {
        return recordMapper.query(new Reco(null, userId, null));
    }

    @Override
    public List<Record> findAllByBookId(Integer bookId) {
        return recordMapper.query(new Reco(null, null, bookId));
    }

    @Override
    public List<Record> findAll() {
        return recordMapper.getRecord(null);
    }

    @Override
    public Integer borrowBook(Integer userId, Integer bookId) {
        Book book = bookMapper.get(bookId).get(0);
        if(!book.getState().equals("IL")) throw new RuntimeException("The book is not in the library.");
        book.setState("LO");
        bookMapper.update(book);

        Reco reco = new Reco(null, userId, bookId);
        recordMapper.insertReco(reco);

        Cord cord = new Cord(null, reco.getId(), toolMapper.now(), "BB");
        recordMapper.insertCord(cord);

        return cord.getId();
    }

    @Override
    public Integer returnBook(Integer recoId) {
        Reco reco = recordMapper.getReco(recoId).get(0);
        Book book = bookMapper.get(reco.getBookId()).get(0);
        book.setState("IL");
        bookMapper.update(book);

        Cord cord = new Cord(null, recoId, toolMapper.now(), "RB");
        recordMapper.insertCord(cord);

        return cord.getId();
    }

    @Override
    public Integer renewOrder(Integer recoId) {
        Cord cord = new Cord(null, recoId, toolMapper.now(), "RO");
        recordMapper.insertCord(cord);
        return cord.getId();
    }

    @Override
    public Integer reportLost(Integer recoId) {
        Reco reco = recordMapper.getReco(recoId).get(0);
        Book book = bookMapper.get(reco.getBookId()).get(0);
        book.setState("NF");
        bookMapper.update(book);

        Cord cord = new Cord(null, recoId, toolMapper.now(), "RL");
        recordMapper.insertCord(cord);
        return cord.getId();
    }
}
