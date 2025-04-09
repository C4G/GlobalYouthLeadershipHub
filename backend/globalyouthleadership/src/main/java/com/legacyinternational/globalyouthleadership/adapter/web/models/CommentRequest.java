package com.legacyinternational.globalyouthleadership.adapter.web.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentRequest {
    private String content;
    private Long parentCommentId;
}
