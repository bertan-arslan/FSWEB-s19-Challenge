package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_shouldCreateUser_whenUsernameNotExists() {
        String username = "alice";
        String rawPassword = "123456";

        when(userRepository.findByUsername(username)).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn("encoded-pass");

        User saved = User.builder()
                .id(1L)
                .username(username)
                .password("encoded-pass")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.register(username, rawPassword);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("alice", result.getUsername());
        assertEquals("encoded-pass", result.getPassword());

        verify(userRepository).findByUsername(username);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getById_shouldThrow_whenUserNotFound() {

        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getById(id)
        );
        assertEquals("User not found", ex.getMessage());
    }
}
