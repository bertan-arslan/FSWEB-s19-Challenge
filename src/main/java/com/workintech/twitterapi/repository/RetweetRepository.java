package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    boolean existsByTweetIdAndUserId(Long tweetId, Long userId);

    Retweet findByTweetIdAndUserId(Long tweetId, Long userId);
}
