package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.CommentResponse;
import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TweetService tweetService;
    private final UserService userService;

    public CommentResponse create(String content, Long tweetId, Long userId){
        Tweet tweet = tweetService.getEntityById(tweetId);
        User user = userService.getById(userId);

        Comment comment = Comment.builder()
                .content(content)
                .tweet(tweet)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return toResponse(saved);
    }

    public CommentResponse update(Long commentId, String newContent, Long requesterId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new IllegalArgumentException("Comment not found");
        }

        if (!comment.getUser().getId().equals(requesterId)) {
            throw new IllegalArgumentException("Only comment owner can update");
        }

        comment.setContent(newContent);
        Comment updated = commentRepository.save(comment);
        return toResponse(updated);
    }

    public void delete(Long commentId, Long requesterId){
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new IllegalArgumentException("Comment not found");
        }

        Long tweetOwnerId = comment.getTweet().getUser().getId();
        boolean isCommentOwner = comment.getUser().getId().equals(requesterId);
        boolean isTweetOwner = tweetOwnerId.equals(requesterId);

        if (!isCommentOwner && !isTweetOwner) {
            throw new IllegalArgumentException("Only comment owner or tweet owner can delete");
        }

        commentRepository.delete(comment);
    }

    private CommentResponse toResponse(Comment c){
        return new CommentResponse(
                c.getId(),
                c.getContent(),
                c.getTweet().getId(),
                c.getUser().getId()
        );
    }
}
