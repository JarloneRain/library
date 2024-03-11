package com.demo.library.mapper;

import com.demo.library.entity.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookMapperTest {
    Random random;
    @Autowired
    BookMapper bookMapper;

    @Before
    public void init() {
        random = new Random(new Date().getTime());
    }

    @Test //已通过
    public void test() {
        //测试添加
        Book[] books = new Book[10];
        Book theBook;
        for (int i = 0; i < 10; i++) {
            books[i] = new Book(null, Long.toString(random.nextLong() % 100000), "IL");
            bookMapper.insert(books[i]);
        }

        for (int i = 0; i < 10; i++) {
            theBook = bookMapper.get(books[i].getId()).get(0);
            Assert.assertEquals("i=" + i, books[i].getTitle(), theBook.getTitle());
            Assert.assertEquals("i=" + i, books[i].getState(), theBook.getState());
        }

        //测试更新
        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()) {
                books[i].setState("LO");
                bookMapper.update(books[i]);
            }
        }

        for (int i = 0; i < 10; i++) {
            theBook = bookMapper.get(books[i].getId()).get(0);
            Assert.assertEquals(books[i].getTitle(), theBook.getTitle());
            Assert.assertEquals(books[i].getState(), theBook.getState());
            books[i].setId(theBook.getId());
        }
    }
}
