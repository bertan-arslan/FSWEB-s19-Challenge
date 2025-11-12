package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.LikeRequest;
import com.workintech.twitterapi.dto.LikeResponse;
import com.workintech.twitterapi.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<LikeResponse> like(@Valid @RequestBody LikeRequest req){
        return ResponseEntity.ok(likeService.like(req.getTweetId(), req.getUserId()));
    }

    @PostMapping("/dislike")
    public ResponseEntity<LikeResponse> dislike(@Valid @RequestBody LikeRequest req){
        return ResponseEntity.ok(likeService.dislike(req.getTweetId(), req.getUserId()));
    }
}
