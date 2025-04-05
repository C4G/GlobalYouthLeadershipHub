package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectServiceImpl projectService;

    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectResponse> createProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Principal principal
    ) {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name(name)
                .description(description)
                .uploadedFile(file)
                .build();
        try {
            ProjectRequest.validateInput(projectRequest);
            String email = principal.getName();
            projectRequest.setProjectOwner(email);

            Project project = projectService.createProject(projectRequest);
            ProjectResponse projectResponse = ProjectResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .projectOwner(project.getProjectOwner())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/api/projects/%s/image", project.getId()))
                    .createdDate(String.valueOf(project.getCreatedAt()))
                    .createdBy(project.getCreatedBy())
                    .lastModifiedBy(project.getUpdatedBy())
                    .lastModifiedDate(String.valueOf(project.getUpdatedAt()))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProjectImage(@PathVariable Long id) {
        if (Objects.isNull(id) || id.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        Optional<Project> projectById = projectService.getProjectById(id);
        if (projectById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        Project project = projectById.get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(project.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + project.getFileName() + "\"")
                .body(project.getFileData());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        Optional<Project> projectById = projectService.getProjectById(id);
        if (projectById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        try {
            Project project = projectById.get();
            return ResponseEntity.ok(ProjectResponse.builder()
                            .id(project.getId())
                    .name(project.getName())
                    .projectOwner(project.getProjectOwner())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/api/projects/%s/image", project.getId()))
                    .createdDate(String.valueOf(project.getCreatedAt()))
                    .createdBy(project.getCreatedBy())
                    .lastModifiedBy(project.getUpdatedBy())
                    .lastModifiedDate(String.valueOf(project.getUpdatedAt()))
                    .build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving project");
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        try {
            List<Project> allProjects = projectService.getAllProjects();
            List<ProjectResponse> projectResponses = allProjects.stream()
                    .sorted(Comparator.comparing(Project::getUpdatedAt).reversed())
                    .map(project -> ProjectResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .projectOwner(project.getProjectOwner())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/api/projects/%s/image", project.getId()))
                    .createdDate(String.valueOf(project.getCreatedAt()))
                    .createdBy(project.getCreatedBy())
                    .lastModifiedBy(project.getUpdatedBy())
                    .lastModifiedDate(String.valueOf(project.getUpdatedAt()))
                    .build()).toList();

            return ResponseEntity.ok(projectResponses);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving all projects");
        }
    }
}
