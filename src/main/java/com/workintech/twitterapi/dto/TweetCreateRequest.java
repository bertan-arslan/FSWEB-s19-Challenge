package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TweetCreateRequest {
    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}
