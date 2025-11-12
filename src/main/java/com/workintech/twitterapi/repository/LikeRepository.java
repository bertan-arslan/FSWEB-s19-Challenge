package com.workintech.twitterapi.repository;

import com.workintech.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByTweetIdAndUserId(Long tweetId, Long userId);

    Like findByTweetIdAndUserId(Long tweetId, Long userId);

    long countByTweetId(Long tweetId);
}
