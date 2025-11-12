package com.workintech.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetweetResponse {
    private Long id;
    private Long tweetId;
    private Long userId;
}
