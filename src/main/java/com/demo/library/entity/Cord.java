package com.demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cord {
    Integer id;
    Integer recoId;
    Date time;
    String type;
}

