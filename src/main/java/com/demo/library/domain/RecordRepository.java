package com.demo.library.domain;

import com.demo.library.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByUserId(Long userId);

    List<Record> findAllByBookId(Long bookId);

}
