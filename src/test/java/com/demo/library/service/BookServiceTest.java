package com.demo.library.service;

import com.demo.library.entity.Book;
import com.demo.library.mapper.BookMapper;
import com.demo.library.service.impl.BookServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceTest {
    // Simulate a database.
    List<Book> books;
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
                    book.getState() == null ? b.getState() : book.getTitle()
            ) : b).toList();
            return 1;
        }
    };
    BookService bookService = new BookServiceImpl(bookMapper);

    @BeforeEach
    public void initBooks() {
        books = Lists.newArrayList(
                new Book(0, "aaa", "IL"), new Book(1, "bbb", "IL"),
                new Book(2, "ccc", "IL"), new Book(3, "ddd", "IL"),
                new Book(4, "abc", "IL"), new Book(5, "ABc", "IL"),
                new Book(6, "AaA", "IL"), new Book(7, "fws", "IL"),
                new Book(8, "afa", "IL"), new Book(9, "sing", "IL")
        );
    }

    @Test
    public void testGetOne() {
        Book book;
        for(int id = 0; id < 10; id++) {
            book = bookService.getOne(id);
            Assertions.assertEquals(books.get(id), book);
        }
    }

    @Test
    public void testGetAll() {
        List<Book> allBooks = bookService.getAll();
        Assertions.assertEquals(bookMapper.get(null), allBooks);
    }

    @Test
    public void testSearchTitle() {
        List<Book> result = bookService.searchTitle("a");
        Assertions.assertEquals(bookMapper.query("a"), result);
    }

    @Test
    public void testAddBook() {
        bookService.addBook("anew");
        Assertions.assertEquals("anew", books.get(books.size() - 1).getTitle());
    }


//    @InjectMocks
//    BookServiceImpl bookService;
//    @Mock
//    BookMapper bookMapper;
//    List<Book> books = Lists.newArrayList(
//            new Book(1, "aaa", "IL"), new Book(2, "bbb", "IL"),
//            new Book(3, "ccc", "IL"), new Book(4, "ddd", "IL"),
//            new Book(5, "abc", "IL"), new Book(6, "ABc", "IL"),
//            new Book(7, "AaA", "IL"), new Book(8, "fws", "IL"),
//            new Book(9, "afa", "IL"), new Book(10, "sing", "IL")
//    );
//    Book ilBook = new Book(11, "anew", "IL");
//    Book loBook = new Book(11, "anew", "LO");
//    Book lfBook = new Book(11, "anew", "LF");
//
//    @Before//改成before each的话get one过不去
//    public void setUp() {
////        MockitoAnnotations.openMocks(this);
////        bookService = new BookServiceImpl(bookMapper);
//
//
//        //模拟get
//        Mockito.when(bookMapper.get(null)).thenReturn(books);
//        Mockito.when(bookMapper.get(Mockito.any(Integer.class))).thenReturn(Lists.newArrayList(books.get(0)));
//
//        //模拟insert
//        Mockito.when(bookMapper.insert(Mockito.any(Book.class))).thenReturn(11);
//        //Mockito.doNothing().when(bookMapper).insert(Mockito.any());
//
//        //模拟update
//        //Mockito.when(bookMapper.update(loBook)).thenReturn(books.set(10, loBook) != null ? 1 : 0);
//        //Mockito.when(bookMapper.update(lfBook)).thenReturn(books.set(10, lfBook) != null ? 1 : 0);
//        //模拟query
//        Mockito.when(bookMapper.query("a"))
//                .thenReturn(books.stream().filter(b -> b.getTitle().contains("a")).toList());
//    }
//
//    @Test
//    public void testGetOne() {
////        Book book;
////        for(int id = 1; id < 10; id++) {
////             book = bookService.getOne(id);
////            Assert.assertEquals(books.get(id - 1), book);
////        }
//        Assert.assertEquals(books.get(0), bookService.getOne(1));
//    }
//
//    @Test
//    public void testGetAll() {
//        List<Book> allBooks = bookService.getAll();
//        Assert.assertEquals(bookMapper.get(null), allBooks);
//    }
//
//    @Test
//    public void testSearchTitle() {
//        List<Book> result = bookService.searchTitle("a");
//        Assert.assertEquals(bookMapper.query("a"), result);
//    }
//
//    @Test
//    public void testAddBook() {
//        bookService.addBook("anew");
//        Mockito.verify(bookMapper).insert(Mockito.any(Book.class));
//    }
}
