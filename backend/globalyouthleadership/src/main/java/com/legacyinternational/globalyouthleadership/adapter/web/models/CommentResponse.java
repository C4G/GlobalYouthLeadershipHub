package com.legacyinternational.globalyouthleadership.adapter.web.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String commentOwner;
    private LocalDateTime createdAt;
    private List<CommentResponse> replies;
}
