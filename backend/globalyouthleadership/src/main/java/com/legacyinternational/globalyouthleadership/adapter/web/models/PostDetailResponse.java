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
    private String content;
    private String authorEmail;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private int likeCount;
    private List<CommentResponse> comments;
}
