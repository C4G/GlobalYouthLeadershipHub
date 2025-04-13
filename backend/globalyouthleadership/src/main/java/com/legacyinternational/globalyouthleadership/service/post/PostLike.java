package com.legacyinternational.globalyouthleadership.service.post;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_likes", schema = "SUSTSCH")
@Getter
@Setter
@Builder
@IdClass(PostLikeId.class)
public class PostLike {
    @Id
    private Long postId;

    @Id
    private String userEmail;

    @Column(name = "liked_at", nullable = false, updatable = false)
    private LocalDateTime likedAt;
}
