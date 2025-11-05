package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TweetUpdateRequest {
    @NotBlank
    private String content;
}
