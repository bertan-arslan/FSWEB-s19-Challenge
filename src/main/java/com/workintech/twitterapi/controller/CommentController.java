package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.CommentCreateRequest;
import com.workintech.twitterapi.dto.CommentResponse;
import com.workintech.twitterapi.dto.CommentUpdateRequest;
import com.workintech.twitterapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentCreateRequest req){
        return ResponseEntity.ok(commentService.create(req.getContent(), req.getTweetId(), req.getUserId()));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody CommentUpdateRequest req,
                                                  @RequestParam Long userId){
        return ResponseEntity.ok(commentService.update(id, req.getContent(), userId));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId){
        commentService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
