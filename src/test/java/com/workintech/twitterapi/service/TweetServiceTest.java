package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.TweetResponse;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TweetService tweetService;

    @Test
    void create_shouldSaveTweet_withValidUser() {
        Long userId = 1L;
        String content = "hello";

        User user = User.builder().id(userId).username("alice").build();
        when(userService.getById(userId)).thenReturn(user);

        Tweet saved = Tweet.builder()
                .id(10L)
                .content(content)
                .user(user)
                .build();
        when(tweetRepository.save(any(Tweet.class))).thenReturn(saved);

        TweetResponse response = tweetService.create(content, userId);

        assertEquals(10L, response.getId());
        assertEquals(content, response.getContent());
        assertEquals(userId, response.getUserId());
        assertEquals("alice", response.getUsername());
    }

    @Test
    void delete_shouldThrow_whenRequesterIsNotOwner() {
        // given
        Long tweetId = 5L;
        Long ownerId = 1L;
        Long otherUserId = 2L;

        User owner = User.builder().id(ownerId).build();
        Tweet tweet = Tweet.builder()
                .id(tweetId)
                .content("test")
                .user(owner)
                .build();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> tweetService.delete(tweetId, otherUserId)
        );
        assertEquals("Only owner can delete", ex.getMessage());
    }
}
