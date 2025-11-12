package com.workintech.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LikeResponse {
    private Long tweetId;
    private long likeCount;
    private boolean liked; // bu kullanıcı şu anda like'ladı mı
}