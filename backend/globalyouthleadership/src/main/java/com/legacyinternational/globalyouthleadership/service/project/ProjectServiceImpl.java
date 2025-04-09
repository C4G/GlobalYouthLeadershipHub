package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ProjectRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.ProjectRepository;
import com.legacyinternational.globalyouthleadership.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectRequest projectRequest) {
        try {
            Project projectToSave = Project.builder()
                    .name(projectRequest.getName())
                    .description(projectRequest.getDescription())
                    .projectOwner(projectRequest.getProjectOwner())
                    .fileName(projectRequest.getUploadedFile().getOriginalFilename())
                    .fileType(projectRequest.getUploadedFile().getContentType())
                    .fileData(projectRequest.getUploadedFile().getBytes())
                    .createdBy(projectRequest.getProjectOwner())
                    .createdAt(LocalDateTime.now())
                    .updatedBy(projectRequest.getProjectOwner())
                    .updatedAt(LocalDateTime.now())
                    .build();
            return projectRepository.save(projectToSave);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
