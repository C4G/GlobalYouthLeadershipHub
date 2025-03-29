package com.legacyinternational.globalyouthleadership.adapter.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResponse {
    private Long id;
    private Long userId;
    private String description;
    private String weblinkLink;
    private String createdBy;
}
