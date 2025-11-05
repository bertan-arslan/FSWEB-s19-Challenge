package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.TweetCreateRequest;
import com.workintech.twitterapi.dto.TweetResponse;
import com.workintech.twitterapi.dto.TweetUpdateRequest;
import com.workintech.twitterapi.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TweetController {

    private final TweetService tweetService;

    @PostMapping("/tweet")
    public ResponseEntity<TweetResponse> create(@Valid @RequestBody TweetCreateRequest req){
        return ResponseEntity.ok(tweetService.create(req.getContent(), req.getUserId()));
    }

    @GetMapping("/tweet/findByUserId")
    public ResponseEntity<List<TweetResponse>> findByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(tweetService.findByUserId(userId));
    }

    @GetMapping("/tweet/findById")
    public ResponseEntity<TweetResponse> findById(@RequestParam Long id){
        return ResponseEntity.ok(tweetService.findById(id));
    }

    @PutMapping("/tweet/{id}")
    public ResponseEntity<TweetResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody TweetUpdateRequest req,
                                                @RequestParam Long userId){
        return ResponseEntity.ok(tweetService.update(id, req.getContent(), userId));
    }

    @DeleteMapping("/tweet/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId){
        tweetService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
