package com.legacyinternational.globalyouthleadership.adapter.web.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String postOwner;
    private boolean isLikedByLoggedInUser;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private int likeCount;
    private int commentCount;
    private List<CommentResponse> comments;
}
