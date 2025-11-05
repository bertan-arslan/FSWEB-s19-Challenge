package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String username, String password) {
        userRepository.findByUsername(username)
                .ifPresent(u -> { throw new IllegalArgumentException("Username already exists"); });

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    public User login(String username, String password) {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            return opt.get();
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
