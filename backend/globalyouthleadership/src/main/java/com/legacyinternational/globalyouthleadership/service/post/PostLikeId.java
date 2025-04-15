package com.legacyinternational.globalyouthleadership.service.post;

import com.legacyinternational.globalyouthleadership.service.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class PostLikeId implements Serializable {
    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Id
    @Column(name = "user_email", nullable = false)
    private String userEmail;
}
