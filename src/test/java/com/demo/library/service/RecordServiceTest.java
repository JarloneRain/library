package com.demo.library.service;

import com.demo.library.entity.Book;
import com.demo.library.entity.Cord;
import com.demo.library.entity.Reco;
import com.demo.library.entity.Record;
import com.demo.library.mapper.BookMapper;
import com.demo.library.mapper.RecordMapper;
import com.demo.library.mapper.ToolMapper;
import com.demo.library.service.impl.BookServiceImpl;
import com.demo.library.service.impl.RecordServiceImpl;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Date;
import java.util.List;

public class RecordServiceTest {
    List<Book> books = Lists.newArrayList(
            new Book(0, "aaa", "IL"), new Book(1, "bbb", "NF"),
            new Book(2, "ccc", "IL"), new Book(3, "ddd", "IL"),
            new Book(4, "abc", "IL"), new Book(5, "ABc", "IL"),
            new Book(6, "AaA", "IL"), new Book(7, "fws", "IL"),
            new Book(8, "afa", "IL"), new Book(9, "sing", "IL")
    );
    BookMapper bookMapper = new BookMapper() {
        @Override
        public List<Book> get(Integer id) {
            return books.stream().filter(b -> id == null || b.getId().equals(id)).toList();
        }

        @Override
        public List<Book> query(String keyword) {
            return books.stream().filter(b -> b.getTitle().contains(keyword)).toList();
        }

        @Override
        public Integer insert(Book book) {
            return books.add(new Book(books.size(), book.getTitle(), book.getState())) ? 1 : 0;
        }

        @Override
        public Integer update(Book book) {
            books = books.stream().map(b -> b.getId().equals(book.getId()) ? new Book(
                    book.getId(),
                    book.getTitle() == null ? b.getTitle() : book.getTitle(),
                    book.getState() == null ? b.getState() : book.getState()
            ) : b).toList();
            return 1;
        }
    };

    List<Reco> recos = Lists.newArrayList(
            new Reco(0, 0, 0),
            new Reco(1, 1, 1)
    );
    List<Cord> cords = Lists.newArrayList(
            new Cord(0, 0, new Date(123456), "BB"),
            new Cord(1, 0, new Date(654321), "RB"),
            new Cord(2, 1, new Date(123456), "BB"),
            new Cord(3, 1, new Date(654321), "RL")
    );
    RecordMapper recordMapper = new RecordMapper() {
        @Override
        public List<Record> getRecord(Integer id) {
            return recos.stream().filter(r -> id == null || r.getId().equals(id))
                    .map(r -> new Record(
                            r.getId(), r,
                            cords.stream().filter(c -> c.getId().equals(r.getId())).toList()
                    )).toList();
        }

        @Override
        public List<Reco> getReco(Integer id) {
            return recos.stream().filter(r -> id == null || r.getId().equals(id)).toList();
        }

        @Override
        public List<Cord> getCord(Integer id) {
            return cords.stream().filter(c -> id == null || c.getId().equals(id)).toList();
        }

        @Override
        public List<Record> query(Reco reco) {
            return getRecord(null).stream().filter(r -> true
                    && (reco.getUserId() == null || r.getReco().getUserId().equals(reco.getUserId()))
                    && (reco.getBookId() == null || r.getReco().getBookId().equals(reco.getBookId()))
            ).toList();
        }

        @Override
        public Integer insertReco(Reco reco) {
            reco.setId(recos.size());
            return recos.add(new Reco(reco.getId(), reco.getUserId(), reco.getBookId())) ? 1 : 0;
        }

        @Override
        public Integer insertCord(Cord cord) {
            cord.setId(cords.size());
            return cords.add(new Cord(cord.getId(), cord.getRecoId(), cord.getTime(), cord.getType())) ? 1 : 0;
        }
    };
    RecordService recordService = new RecordServiceImpl(recordMapper, bookMapper, Date::new);

    @Test
    public void testFindAllByUserId() {
        Assertions.assertEquals(
                recordMapper.query(new Reco(null, 1, null)),
                recordService.findAllByUserId(1)
        );
    }

    @Test
    public void testFindAllByBookId() {
        Assertions.assertEquals(
                recordMapper.query(new Reco(null, null, 1)),
                recordService.findAllByBookId(1)
        );
    }

    @Test
    public void testFindAll() {
        Assertions.assertEquals(
                recordMapper.getRecord(null),
                recordService.findAll()
        );
    }

    Reco newReco = new Reco(2, 3, 4);

    @Test
    public void testBorrowBook() {
        Integer id = recordService.borrowBook(3, 4);

        Assertions.assertEquals("LO", books.get(newReco.getBookId()).getState(), books.get(newReco.getBookId()).toString());

        Assertions.assertEquals(newReco, recordMapper.getReco(recordMapper.getCord(id).get(0).getRecoId()).get(0));
        Assertions.assertEquals("BB", recordMapper.getCord(id).get(0).getType());
    }

    @Test
    public void testReturnBook() {
        recos.add(newReco);

        Integer id = recordService.returnBook(newReco.getId());
        Assertions.assertEquals("IL", books.get(newReco.getBookId()).getState());

        Assertions.assertEquals(newReco, recordMapper.getReco(recordMapper.getCord(id).get(0).getRecoId()).get(0));
        Assertions.assertEquals("RB", recordMapper.getCord(id).get(0).getType());
    }

    @Test
    public void testRenewOrder() {
        recos.add(newReco);

        Integer id = recordService.renewOrder(newReco.getId());

        Assertions.assertEquals(newReco, recordMapper.getReco(recordMapper.getCord(id).get(0).getRecoId()).get(0));
        Assertions.assertEquals("RO", recordMapper.getCord(id).get(0).getType());
    }

    @Test
    public void testReportLost() {
        recos.add(newReco);

        Integer id = recordService.reportLost(newReco.getId());
        Assertions.assertEquals("NF", books.get(newReco.getBookId()).getState());

        Assertions.assertEquals(newReco, recordMapper.getReco(recordMapper.getCord(id).get(0).getRecoId()).get(0));
        Assertions.assertEquals("RL", recordMapper.getCord(id).get(0).getType());
    }

}
