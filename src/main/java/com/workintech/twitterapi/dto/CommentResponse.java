package com.workintech.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private Long tweetId;
    private Long userId;
}
