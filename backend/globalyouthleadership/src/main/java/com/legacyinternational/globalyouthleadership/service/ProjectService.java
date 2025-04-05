package com.legacyinternational.globalyouthleadership.service;

import com.legacyinternational.globalyouthleadership.adapter.web.ProjectRequest;
import com.legacyinternational.globalyouthleadership.service.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project createProject(ProjectRequest projectRequest);
    Optional<Project> getProjectById(Long id);
//    List<Project> getProjectsByUserId(Long userId);
    List<Project> getAllProjects();
}
