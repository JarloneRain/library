package com.demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reco {
    Integer id;
    Integer userId;
    Integer bookId;
}