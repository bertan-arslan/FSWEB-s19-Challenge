package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.RetweetResponse;
import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.RetweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetService tweetService;
    private final UserService userService;

    public RetweetResponse create(Long tweetId, Long userId) {
        Tweet tweet = tweetService.getEntityById(tweetId);
        User user = userService.getById(userId);

        if (retweetRepository.existsByTweetIdAndUserId(tweetId, userId)) {
            throw new IllegalArgumentException("User already retweeted this tweet");
        }

        Retweet retweet = Retweet.builder()
                .tweet(tweet)
                .user(user)
                .build();

        Retweet saved = retweetRepository.save(retweet);

        return new RetweetResponse(
                saved.getId(),
                saved.getTweet().getId(),
                saved.getUser().getId()
        );
    }

    public void delete(Long retweetId, Long requesterId) {
        Retweet retweet = retweetRepository.findById(retweetId).orElse(null);
        if (retweet == null) {
            throw new IllegalArgumentException("Retweet not found");
        }

        if (!retweet.getUser().getId().equals(requesterId)) {
            throw new IllegalArgumentException("Only retweet owner can delete");
        }

        retweetRepository.delete(retweet);
    }
}
