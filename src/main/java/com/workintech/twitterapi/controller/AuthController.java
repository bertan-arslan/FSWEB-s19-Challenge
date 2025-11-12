package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.UserLoginRequest;
import com.workintech.twitterapi.dto.UserRegisterRequest;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegisterRequest req){
        User created = userService.register(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest req){
        User user = userService.findByUsername(req.getUsername());

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return ResponseEntity.ok("Login successful");
    }
}
