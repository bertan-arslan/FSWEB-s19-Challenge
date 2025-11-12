package com.workintech.twitterapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tweet_id","user_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tweet_id", nullable=false)
    private Tweet tweet;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
