package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.LikeResponse;
import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final TweetService tweetService;
    private final UserService userService;

    public LikeResponse like(Long tweetId, Long userId){
        Tweet tweet = tweetService.getEntityById(tweetId);
        User user = userService.getById(userId);

        if (likeRepository.existsByTweetIdAndUserId(tweetId, userId)) {
            long count = likeRepository.countByTweetId(tweetId);
            return new LikeResponse(tweetId, count, true);
        }

        Like like = Like.builder()
                .tweet(tweet)
                .user(user)
                .build();

        likeRepository.save(like);

        long count = likeRepository.countByTweetId(tweetId);
        return new LikeResponse(tweetId, count, true);
    }

    public LikeResponse dislike(Long tweetId, Long userId){
        Like like = likeRepository.findByTweetIdAndUserId(tweetId, userId);

        if (like != null) {
            likeRepository.delete(like);
        }

        long count = likeRepository.countByTweetId(tweetId);
        return new LikeResponse(tweetId, count, false);
    }
}
