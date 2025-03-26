package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    private ProjectServiceImpl projectService;
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        projectService = mock(ProjectServiceImpl.class);
        projectController = new ProjectController(projectService);
    }

    @Test
    void testCreateProject_ReturnsCreatedProject() {
        Project inputProject = new Project();
        inputProject.setUserId(1L);
        inputProject.setDescription("Sustainability Initiative");
        inputProject.setWeblinkLink("https://example.com");
        inputProject.setCreatedBy("Admin");

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setUserId(1L);
        savedProject.setDescription("Sustainability Initiative");
        savedProject.setWeblinkLink("https://example.com");
        savedProject.setCreatedBy("Admin");

        when(projectService.createProject(inputProject)).thenReturn(savedProject);

        ResponseEntity<Project> response = projectController.createProject(inputProject);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedProject, response.getBody());
        verify(projectService, times(1)).createProject(inputProject);
    }

    @Test
    void testGetProjectById_ProjectExists() {
        Long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        mockProject.setDescription("Youth Climate Campaign");

        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));

        ResponseEntity<Project> response = projectController.getProjectById(projectId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProject, response.getBody());
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    void testGetProjectById_ProjectNotFound() {
        Long projectId = 99L;

        when(projectService.getProjectById(projectId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            projectController.getProjectById(projectId);
        });

//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//        assertEquals("Project not found", exception.getMessage());

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    void testGetProjectsByUserId_ReturnsProjectList() {
        Long userId = 42L;

        List<Project> mockProjects = Arrays.asList(
                Project.builder().id(1L).description("Climate App").userId(userId).build(),
                Project.builder().id(2L).description("Tree Planting Tracker").userId(userId).build()
        );

        when(projectService.getProjectsByUserId(userId)).thenReturn(mockProjects);

        ResponseEntity<List<Project>> response = projectController.getProjectsByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProjects, response.getBody());
        verify(projectService, times(1)).getProjectsByUserId(userId);
    }

    @Test
    void testGetAllProjects_ReturnsAllProjects() {
        List<Project> allProjects = Arrays.asList(
                Project.builder().id(3L).description("Recycling Game").userId(100L).build(),
                Project.builder().id(4L).description("Plastic Audit").userId(101L).build()
        );

        when(projectService.getAllProjects()).thenReturn(allProjects);

        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(allProjects, response.getBody());
        verify(projectService, times(1)).getAllProjects();
    }
}