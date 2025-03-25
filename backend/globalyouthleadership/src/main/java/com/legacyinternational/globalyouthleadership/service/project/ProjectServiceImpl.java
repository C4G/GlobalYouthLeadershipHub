package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.infrastructure.repositories.ProjectRepository;
import com.legacyinternational.globalyouthleadership.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
