package com.demo.library.controller;

import com.demo.library.domain.User;
import com.demo.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Get all the users.")
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Data
    @Schema
    public static class UserJson {
        String name;
    }

    @PostMapping("/")
    @Operation(summary = "Add a new user.", description = "Implemented by POST necessary information.")
    public String registerUserRequest(
            @Parameter(name = "userJson")
            @RequestBody
            UserJson userJson
    ) {
        userService.register(userJson);
        return "success";
    }

    @GetMapping("/search")
    @Operation(summary = "Search user by the keyword.", description = "Implemented by GET the keyword.")
    public List<User> searchUserRequest(
            @Parameter(name = "keyword")
            @RequestParam(name="keyword")
            String keyword
    ) {
        return userService.searchUsers(keyword);
    }
}
