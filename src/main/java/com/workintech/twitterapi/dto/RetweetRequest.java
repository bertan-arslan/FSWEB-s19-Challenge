package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RetweetRequest {

    @NotNull
    private Long tweetId;

    @NotNull
    private Long userId;
}
