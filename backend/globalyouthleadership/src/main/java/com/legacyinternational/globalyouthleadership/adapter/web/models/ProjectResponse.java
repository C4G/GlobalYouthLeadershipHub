package com.legacyinternational.globalyouthleadership.adapter.web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResponse {
    private Long id;
    private String projectOwner;
    private String name;
    private String description;
    private String projectImageUrl;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
}
