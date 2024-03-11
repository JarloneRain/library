package com.demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    Integer id;
    Boolean admin;
    String name;
    String password;

    public User(String name, String password) {
        this.admin = false;
        this.name = name;
        this.password = password;
    }
}