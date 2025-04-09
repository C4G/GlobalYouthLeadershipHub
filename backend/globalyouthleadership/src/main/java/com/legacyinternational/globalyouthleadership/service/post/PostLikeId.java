package com.legacyinternational.globalyouthleadership.service.post;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class PostLikeId implements Serializable {
    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Id
    @Column(name = "user_email", nullable = false)
    private String userEmail;
}
