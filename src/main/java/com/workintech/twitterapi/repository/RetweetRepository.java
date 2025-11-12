package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    boolean existsByTweet_IdAndUser_Id(Long tweetId, Long userId);

    Retweet findByTweet_IdAndUser_Id(Long tweetId, Long userId);
}
