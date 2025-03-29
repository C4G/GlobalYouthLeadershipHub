package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        try {
            ProjectRequest.validateInput(projectRequest);

            Project project = Project.builder()
                    .userId(projectRequest.getUserId())
                    .description(projectRequest.getDescription())
                    .weblinkLink(projectRequest.getWeblinkLink())
                    .build();

            Project savedProject = projectService.createProject(project);

            ProjectResponse response = ProjectResponse.builder()
                    .id(savedProject.getId())
                    .userId(savedProject.getUserId())
                    .description(savedProject.getDescription())
                    .weblinkLink(savedProject.getWeblinkLink())
                    .createdBy(savedProject.getCreatedBy())
                    .build();

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create project");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        try {
            Optional<Project> project = projectService.getProjectById(id);
            return project.map(ResponseEntity::ok).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving project");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(projectService.getProjectsByUserId(userId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving projects");
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            return ResponseEntity.ok(projectService.getAllProjects());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving all projects");
        }
    }
}
