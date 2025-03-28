package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ProjectRequest {
    private Long userId;
    private String description;
    private String weblinkLink;

    public static void validateInput(ProjectRequest request) throws IllegalArgumentException {
        if (Objects.isNull(request.getUserId())) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (Objects.isNull(request.getDescription()) || request.getDescription().length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters");
        }

        if (Objects.isNull(request.getWeblinkLink()) || !request.getWeblinkLink().matches("^(http|https)://.*$")) {
            throw new IllegalArgumentException("Invalid URL, the URL format must be valid");
        }
    }
}
