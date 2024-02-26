package com.demo.library.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "borrow_time")
    Timestamp borrowTime;
    @Column(name = "return_time")
    Timestamp returnTime;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "book_id")
    Long bookId;

    public Record(Timestamp borrowTime, Long userId, Long bookId) {
        this.borrowTime = borrowTime;
        this.returnTime = null;
        this.userId = userId;
        this.bookId = bookId;
    }
}
