package com.demo.library;

import com.demo.library.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    RecordService recordService;

    static void printTitle(String title) {
        System.out.println("========" + title + "========");
    }

    @Test
    public void testUserService() {
        printTitle("TestUserService");


    }

    @Test
    public void testBookService(){
        printTitle("TestBookService");


    }

}
