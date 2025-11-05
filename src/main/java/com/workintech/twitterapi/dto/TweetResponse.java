package com.workintech.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TweetResponse {
    private Long id;
    private String content;
    private Long userId;
    private String username;
}
