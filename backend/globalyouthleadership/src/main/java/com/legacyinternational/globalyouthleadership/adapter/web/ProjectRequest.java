package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
public class ProjectRequest {

    private String name;
    private String description;
    private MultipartFile uploadedFile;
    private String projectOwner;

    public static void validateInput(ProjectRequest request) throws IllegalArgumentException {
        if (Objects.isNull(request.getName()) || request.getName().length() > 255) {
            throw new IllegalArgumentException("User ID is required and must be less than 255 characters");
        }

        if (Objects.isNull(request.getDescription()) || request.getDescription().length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters");
        }

        String type = request.getUploadedFile().getContentType();
        if (!List.of("image/jpeg", "image/png").contains(type)) {
            throw new IllegalArgumentException("Only JPEG and PNG formats are supported");
        }
    }
}
