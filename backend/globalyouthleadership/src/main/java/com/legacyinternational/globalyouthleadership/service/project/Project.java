package com.legacyinternational.globalyouthleadership.service.project;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects", schema = "SUSTSCH")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Column(name = "weblink_link")
    private String weblinkLink;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static void validateInput(Project request) throws IllegalArgumentException {
        if (Objects.isNull(request.getUserId())) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (Objects.isNull(request.getDescription()) || request.getDescription().length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters");
        }
        if (Objects.isNull(request.getWeblinkLink()) || !request.getWeblinkLink().matches("^(http|https)://.*$") || request.getDescription().length() > 255) {
            throw new IllegalArgumentException("Invalid URL, the URL format must be valid and cannot exceed 255 characters");
        }
    }
}
