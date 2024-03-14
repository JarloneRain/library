package com.demo.library.controller;

import com.demo.library.entity.User;
import com.demo.library.service.UserService;
import com.demo.library.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @Operation(summary = "[Admin]Get all the users.")
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestAttribute(name = "ID")
            Integer ID
    ) {
        return userService.getOne(ID).getAdmin() ?
                new ResponseEntity<>(userService.getAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/search")
    @Operation(summary = "[Admin]Search user by the keyword.", description = "Implemented by GET the keyword.")
    public ResponseEntity<List<User>> searchUserRequest(
            @Parameter(name = "keyword")
            @RequestParam(name = "keyword")
            String keyword,
            @RequestAttribute(name = "ID")
            Integer ID
    ) {
        return userService.getOne(ID).getAdmin() ?
                new ResponseEntity<>(userService.searchName(keyword), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Data
    @Schema
    public static class RegisterJson {
        String name;
        String password;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user.", description = "Implemented by POST necessary information.")
    public String registerUserRequest(
            @Parameter(name = "registerJson")
            @RequestBody
            RegisterJson registerJson
    ) {
        Integer userId = userService.register(registerJson.getName(), registerJson.getPassword());
        return "id:" + userId;
    }

    @Data
    @Schema
    public static class LoginJson {
        Integer id;
        String password;
    }

    @PostMapping("/login")
    @Operation(summary = "Log in.", description = "Implemented by POST necessary information.")
    public Map<String, Object> loginRequest(
            @Parameter(name = "loginJson")
            @RequestBody
            LoginJson loginJson
    ) {
        Map<String, Object> map = new HashMap<>();
        if(userService.login(loginJson.getId(), loginJson.getPassword())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", loginJson.getId().toString());
            String token = JwtUtils.getToken(payload);
            map.put("state", true);
            map.put("msg", "success");
            map.put("token", token);
        } else {
            map.put("state", false);
            map.put("msg", "failed");
        }
        return map;
    }


}
