package com.demo.library.mapper;

import com.demo.library.entity.Cord;
import com.demo.library.entity.Reco;
import com.demo.library.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {
    List<Record> getRecord(Integer id);

    List<Reco> getReco(Integer id);

    List<Cord> getCord(Integer id);

    List<Record> query(Reco reco);

    Integer insertReco(Reco reco);

    Integer insertCord(Cord cord);
}
