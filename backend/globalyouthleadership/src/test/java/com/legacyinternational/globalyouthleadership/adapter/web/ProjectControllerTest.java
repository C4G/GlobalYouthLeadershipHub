package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.ProjectRequest;
import com.legacyinternational.globalyouthleadership.adapter.web.models.ProjectResponse;
import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    private ProjectServiceImpl projectService;
    private ProjectController controller;
    private Principal mockPrincipal;

    @BeforeEach
    void setup() {
        projectService = mock(ProjectServiceImpl.class);
        controller = new ProjectController(projectService);
        mockPrincipal = () -> "testuser@example.com";
    }

    @Test
    void testCreateProjectSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "bytes".getBytes());

        Project mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setName("Test Project");
        mockProject.setDescription("A cool project");
        mockProject.setProjectOwner("testuser@example.com");
        mockProject.setCreatedBy("testuser@example.com");
        mockProject.setCreatedAt(LocalDateTime.now());
        mockProject.setUpdatedBy("testuser@example.com");
        mockProject.setUpdatedAt(LocalDateTime.now());

        when(projectService.createProject(any())).thenReturn(mockProject);

        ResponseEntity<ProjectResponse> response = controller.createProject("Test Project", "A cool project", file, mockPrincipal);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Project");
        verify(projectService).createProject(any(ProjectRequest.class));
    }

    @Test
    void testCreateProjectInvalidInputThrowsException() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.createProject("", "", null, mockPrincipal);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetProjectImageSuccess() {
        Project project = new Project();
        project.setId(1L);
        project.setFileName("img.jpg");
        project.setFileType("image/jpeg");
        project.setFileData("imagebytes".getBytes());

        when(projectService.getProjectById(1L)).thenReturn(Optional.of(project));

        ResponseEntity<byte[]> response = controller.getProjectImage(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("imagebytes".getBytes());
    }

    @Test
    void testGetProjectImageInvalidId() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.getProjectImage(-1L);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetProjectImageNotFound() {
        when(projectService.getProjectById(99L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.getProjectImage(99L);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetProjectByIdSuccess() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("A test");
        project.setProjectOwner("testuser@example.com");
        project.setCreatedBy("testuser@example.com");
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedBy("testuser@example.com");
        project.setUpdatedAt(LocalDateTime.now());

        when(projectService.getProjectById(1L)).thenReturn(Optional.of(project));

        ResponseEntity<ProjectResponse> response = controller.getProjectById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Project");
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectService.getProjectById(404L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.getProjectById(404L);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetAllProjects() {
        Project p1 = new Project();
        p1.setId(1L);
        p1.setName("Proj1");
        p1.setProjectOwner("testuser@example.com");
        p1.setCreatedAt(LocalDateTime.now());
        p1.setCreatedBy("admin");
        p1.setUpdatedAt(LocalDateTime.now());
        p1.setUpdatedBy("admin");

        when(projectService.getAllProjects()).thenReturn(List.of(p1));

        ResponseEntity<List<ProjectResponse>> response = controller.getAllProjects();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    void testGetAllProjectsFailure() {
        when(projectService.getAllProjects()).thenThrow(new RuntimeException("fail"));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            controller.getAllProjects();
        });
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}