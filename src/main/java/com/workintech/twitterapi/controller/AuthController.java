package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.UserLoginRequest;
import com.workintech.twitterapi.dto.UserRegisterRequest;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterRequest req){
        return ResponseEntity.ok(userService.register(req.getUsername(), req.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody UserLoginRequest req){
        return ResponseEntity.ok(userService.login(req.getUsername(), req.getPassword()));
    }
}
