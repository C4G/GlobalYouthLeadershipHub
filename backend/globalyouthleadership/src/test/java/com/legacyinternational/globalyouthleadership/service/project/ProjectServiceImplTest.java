package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ProjectRequest;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceImplTest {

    private ProjectRepository projectRepository;
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setup() {
        projectRepository = mock(ProjectRepository.class);
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    void testCreateProjectSuccess() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "data".getBytes());

        ProjectRequest request = ProjectRequest.builder()
                .name("Test Project")
                .description("A sample project")
                .projectOwner("user@example.com")
                .uploadedFile(file)
                .build();

        Project mockSavedProject = Project.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .projectOwner(request.getProjectOwner())
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileData(file.getBytes())
                .createdBy(request.getProjectOwner())
                .createdAt(LocalDateTime.now())
                .updatedBy(request.getProjectOwner())
                .updatedAt(LocalDateTime.now())
                .build();

        when(projectRepository.save(any(Project.class))).thenReturn(mockSavedProject);

        Project result = projectService.createProject(request);

        assertThat(result.getName()).isEqualTo("Test Project");
        assertThat(result.getProjectOwner()).isEqualTo("user@example.com");
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void testCreateProjectThrowsExceptionOnIOException() throws IOException {
        MockMultipartFile file = mock(MockMultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenThrow(new IOException("IO Error"));

        ProjectRequest request = ProjectRequest.builder()
                .name("Invalid")
                .description("Bad file")
                .projectOwner("user@example.com")
                .uploadedFile(file)
                .build();

        assertThrows(ResponseStatusException.class, () -> projectService.createProject(request));
    }

    @Test
    void testGetProjectByIdReturnsResult() {
        Project mockProject = new Project();
        mockProject.setId(1L);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));

        Optional<Project> result = projectService.getProjectById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.getProjectById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void testGetAllProjectsReturnsList() {
        when(projectRepository.findAll()).thenReturn(List.of(new Project(), new Project()));

        List<Project> projects = projectService.getAllProjects();
        assertThat(projects.size()).isEqualTo(2);
    }

    @Test
    void testGetAllProjectsEmpty() {
        when(projectRepository.findAll()).thenReturn(List.of());

        List<Project> projects = projectService.getAllProjects();
        assertThat(projects.size()).isEqualTo(0);
    }
}