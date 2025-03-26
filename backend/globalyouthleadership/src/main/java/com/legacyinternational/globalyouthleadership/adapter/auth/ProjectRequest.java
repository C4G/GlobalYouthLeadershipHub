package com.legacyinternational.globalyouthleadership.adapter.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class ProjectRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Size(max = 255, message = "Weblink cannot exceed 255 characters")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String weblinkLink;

    // Getters and setters

    public static void validateInput(ProjectRequest request) throws IllegalArgumentException {
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
