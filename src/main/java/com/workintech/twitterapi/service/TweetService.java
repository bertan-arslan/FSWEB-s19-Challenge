package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.TweetResponse;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    public TweetResponse create(String content, Long userId){
        User user = userService.getById(userId);

        Tweet tweet = Tweet.builder()
                .content(content)
                .user(user)
                .build();

        Tweet saved = tweetRepository.save(tweet);
        return toResponse(saved);
    }

    public TweetResponse findById(Long id){
        Tweet tweet = tweetRepository.findById(id).orElse(null);
        if (tweet == null) {
            throw new IllegalArgumentException("Tweet not found");
        }
        return toResponse(tweet);
    }

    public List<TweetResponse> findByUserId(Long userId){
        return tweetRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TweetResponse update(Long tweetId, String newContent, Long requesterId){
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        if (tweet == null) {
            throw new IllegalArgumentException("Tweet not found");
        }

        if (!tweet.getUser().getId().equals(requesterId)) {
            throw new IllegalArgumentException("Only owner can update");
        }

        tweet.setContent(newContent);
        Tweet updated = tweetRepository.save(tweet);
        return toResponse(updated);
    }

    public void delete(Long tweetId, Long requesterId){
        Tweet tweet = tweetRepository.findById(tweetId).orElse(null);
        if (tweet == null) {
            throw new IllegalArgumentException("Tweet not found");
        }

        if (!tweet.getUser().getId().equals(requesterId)) {
            throw new IllegalArgumentException("Only owner can delete");
        }

        tweetRepository.delete(tweet);
    }

    public Tweet getEntityById(Long id){
        Tweet tweet = tweetRepository.findById(id).orElse(null);
        if (tweet == null) {
            throw new IllegalArgumentException("Tweet not found");
        }
        return tweet;
    }

    private TweetResponse toResponse(Tweet t){
        return new TweetResponse(
                t.getId(),
                t.getContent(),
                t.getUser().getId(),
                t.getUser().getUsername()
        );
    }
}
