package com.demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    //MyBatis won't merge cords without the unique mark.
    Integer recordId/* = reco.id */;
    Reco reco;
    List<Cord> cords;
}
