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

        return toResponse(tweetRepository.save(tweet));
    }

    public TweetResponse findById(Long id){
        Tweet t = tweetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));
        return toResponse(t);
    }

    public List<TweetResponse> findByUserId(Long userId){
        return tweetRepository.findByUser_Id(userId)
                .stream().map(this::toResponse).toList();
    }

    public TweetResponse update(Long tweetId, String newContent, Long requesterId){
        Tweet t = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));
        if(!t.getUser().getId().equals(requesterId)){
            throw new IllegalArgumentException("Only owner can update");
        }
        t.setContent(newContent);
        return toResponse(tweetRepository.save(t));
    }

    public void delete(Long tweetId, Long requesterId){
        Tweet t = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new IllegalArgumentException("Tweet not found"));
        if(!t.getUser().getId().equals(requesterId)){
            throw new IllegalArgumentException("Only owner can delete");
        }
        tweetRepository.delete(t);
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
