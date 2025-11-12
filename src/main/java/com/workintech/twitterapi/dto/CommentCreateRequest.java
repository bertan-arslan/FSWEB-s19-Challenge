package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateRequest {
    @NotBlank
    private String content;
    @NotNull
    private Long tweetId;
    @NotNull
    private Long userId;
}
