package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ProjectRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.ProjectRepository;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import com.legacyinternational.globalyouthleadership.service.ProjectService;
import com.legacyinternational.globalyouthleadership.service.user.User;
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
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(ProjectRequest projectRequest) {
        try {
            Optional<User> user = userRepository.findByEmail(projectRequest.getProjectOwner());
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
            }

            Project projectToSave = Project.builder()
                    .name(projectRequest.getName())
                    .description(projectRequest.getDescription())
                    .projectOwner(user.get())
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
