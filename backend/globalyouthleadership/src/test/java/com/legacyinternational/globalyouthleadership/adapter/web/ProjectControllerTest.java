package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.*;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.PostImageRepository;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import com.legacyinternational.globalyouthleadership.service.post.PostImage;
import com.legacyinternational.globalyouthleadership.service.project.PostServiceImpl;
import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    private ProjectServiceImpl projectService;
    private ProjectController controller;
    private Principal mockPrincipal;
    private PostServiceImpl postService;
    private PostImageRepository postImageRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        projectService = mock(ProjectServiceImpl.class);
        postService = mock(PostServiceImpl.class);
        postImageRepository = mock(PostImageRepository.class);
        userRepository = mock(UserRepository.class);
        controller = new ProjectController(projectService, postService, postImageRepository, userRepository);
        mockPrincipal = () -> "testuser@example.com";
    }

    @Test
    void testCreateProjectSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "bytes".getBytes());

        Project mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setName("Test Project");
        mockProject.setDescription("A cool project");
        mockProject.setProjectOwner(User.builder().email("testuser@example.com").build());
        mockProject.setCreatedBy("testuser@example.com");
        mockProject.setCreatedAt(LocalDateTime.now());
        mockProject.setUpdatedBy("testuser@example.com");
        mockProject.setUpdatedAt(LocalDateTime.now());

        when(projectService.createProject(any())).thenReturn(mockProject);
        when(userRepository.findByEmail(eq("testuser@example.com"))).thenReturn(Optional.of(User.builder().email("testuser@example.com").build()));

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
        project.setProjectOwner(User.builder().email("testuser@example.com").build());
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
        p1.setProjectOwner(User.builder().email("testuser@example.com").build());
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

    @Test
    void createPost_validInput_returnsResponse() {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "data".getBytes());
        PostResponse response = PostResponse.builder().id(1L).build();
        when(postService.createPost(1L, "content", List.of(file), "title", "test@example.com")).thenReturn(response);

        ResponseEntity<PostResponse> result = controller.createPost(1L, "title", "content", List.of(file), mockPrincipal);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createPost_invalidProjectId_throwsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.createPost(null, "title", "content", List.of(), mockPrincipal));
        assertThat(ex.getReason()).contains("Invalid project id");
    }

    @Test
    void createPost_invalidTitle_throwsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.createPost(1L, "  ", "content", List.of(), mockPrincipal));
        assertThat(ex.getReason()).contains("Invalid title");
    }

    @Test
    void createPost_invalidContent_throwsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.createPost(1L, "title", "", List.of(), mockPrincipal));
        assertThat(ex.getReason()).contains("Invalid content");
    }

    @Test
    void createPost_noImage() {
        PostResponse response = PostResponse.builder().id(1L).build();
        when(postService.createPost(1L, "content", null, "title", "test@example.com")).thenReturn(response);

        ResponseEntity<PostResponse> result = controller.createPost(1L, "title", "content", null, mockPrincipal);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createPost_invalidImageType_throwsException() {
        MockMultipartFile file = new MockMultipartFile("image", "bad.gif", "image/gif", "data".getBytes());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> controller.createPost(1L, "title", "content", List.of(file), mockPrincipal));
        assertThat(ex.getMessage()).contains("Only JPEG and PNG formats are supported");
    }

    @Test
    void getPostsByProject_valid_returnsList() {
        List<PostResponse> posts = List.of(PostResponse.builder().id(1L).build());
        when(postService.getPostsByProject(1L)).thenReturn(posts);
        ResponseEntity<List<PostResponse>> result = controller.getPostsByProject(1L);
        assertThat(result.getBody().size()).isEqualTo(1);
    }

    @Test
    void getPostsByProject_invalid_throwsException() {
        assertThrows(ResponseStatusException.class, () -> controller.getPostsByProject(null));
    }

    @Test
    void getPostDetails_valid_returnsDetails() {
        PostDetailResponse details = PostDetailResponse.builder().id(1L).build();
        when(postService.getPostDetails(1L,1L)).thenReturn(details);
        ResponseEntity<PostDetailResponse> result = controller.getPostDetails(1L,1L);
        assertThat(result.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void getPostDetails_invalidId_throwsException() {
        assertThrows(ResponseStatusException.class, () -> controller.getPostDetails(1L,-1L));
    }

    @Test
    void getPostImage_valid_returnsBytes() {
        Project project = new Project();
        PostImage image = PostImage.builder()
                .id(1L)
                .post(null)
                .fileName("name.png")
                .fileType("image/png")
                .fileData("data".getBytes())
                .build();
        when(projectService.getProjectById(1L)).thenReturn(Optional.of(project));
        when(postService.getPostsByProject(1L)).thenReturn(List.of(PostResponse.builder().id(1L).build()));
        when(postImageRepository.findById(1L)).thenReturn(Optional.of(image));

        ResponseEntity<byte[]> result = controller.getPostImage(1L, 1L, 1L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    void getPostImage_invalidParams_throwException() {
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(null, 1L, 1L));
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(1L, -1L, 1L));
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(1L, 1L, -1L));
    }

    @Test
    void getPostImage_notFoundProjectOrPostOrImage_throws() {
        when(projectService.getProjectById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(1L, 1L, 1L));

        when(projectService.getProjectById(1L)).thenReturn(Optional.of(new Project()));
        when(postService.getPostsByProject(1L)).thenReturn(Collections.emptyList());
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(1L, 1L, 1L));

        when(postService.getPostsByProject(1L)).thenReturn(List.of(PostResponse.builder().id(1L).build()));
        when(postImageRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> controller.getPostImage(1L, 1L, 1L));
    }

    @Test
    void likePost_valid_returnsSuccess() {
        ResponseEntity<ApiResponse> result = controller.likePost(1L, mockPrincipal);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postService).likePost(1L, "testuser@example.com");
    }

    @Test
    void likePost_invalidId_throwsException() {
        assertThrows(ResponseStatusException.class, () -> controller.likePost(null, mockPrincipal));
    }

    @Test
    void addComment_valid_success() {
        CommentRequest request = CommentRequest.builder().content("test").build();
        ResponseEntity<ApiResponse> result = controller.addComment(1L, request, mockPrincipal);
        assertThat(result.getBody().getMessage()).contains("Comment added");
        verify(postService).addComment(1L, "testuser@example.com", request);
    }

    @Test
    void addComment_invalidId_throws() {
        assertThrows(ResponseStatusException.class, () -> controller.addComment(null, new CommentRequest(), mockPrincipal));
    }

    @Test
    void getComments_valid_returnsComments() {
        List<CommentResponse> list = List.of(CommentResponse.builder().id(1L).build());
        when(postService.getComments(1L)).thenReturn(list);
        ResponseEntity<List<CommentResponse>> result = controller.getComments(1L);
        assertThat(result.getBody().size()).isEqualTo(1);
    }

    @Test
    void getComments_invalidId_throws() {
        assertThrows(ResponseStatusException.class, () -> controller.getComments(null));
    }

    @Test
    void testGetProjectsByCurrentUser_returnsUserProjects() {
        Project project = new Project();
        project.setId(1L);
        project.setName("User Project");
        project.setDescription("By current user");
        project.setProjectOwner(User.builder().firstName("Test").lastName("User").build());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setCreatedBy("testuser@example.com");
        project.setUpdatedBy("testuser@example.com");

        when(projectService.getProjectsByCreatedBy("testuser@example.com"))
                .thenReturn(List.of(project));
        when(postService.getPostsByProject(1L))
                .thenReturn(List.of(PostResponse.builder().id(101L).build()));

        ResponseEntity<List<ProjectResponse>> response = controller.getProjectsByCurrentUser(mockPrincipal);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("User Project");
        assertThat(response.getBody().get(0).getPostCount()).isEqualTo(1);
    }

    @Test
    void testGetPostsByCurrentUser_returnsUserPosts() {
        PostResponse post1 = PostResponse.builder()
                .id(1L)
                .title("First Post")
                .content("First post content")
                .postOwner("testuser@example.com")
                .createdAt(LocalDateTime.now())
                .likeCount(0)
                .build();

        PostResponse post2 = PostResponse.builder()
                .id(2L)
                .title("Second Post")
                .content("Second post content")
                .postOwner("testuser@example.com")
                .createdAt(LocalDateTime.now())
                .likeCount(2)
                .build();

        when(postService.getPostsByUser("testuser@example.com")).thenReturn(List.of(post1, post2));

        ResponseEntity<List<PostResponse>> response = controller.getPostsByCurrentUser(mockPrincipal);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("First Post");
        assertThat(response.getBody().get(1).getTitle()).isEqualTo("Second Post");
    }

    @Test
    void unlikePost_valid_returnsSuccess() {
        ResponseEntity<ApiResponse> result = controller.unlikePost(1L, mockPrincipal);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getMessage()).isEqualTo("Post unliked successfully");
        verify(postService).unlikePost(1L, "testuser@example.com");
    }

    @Test
    void unlikePost_nullId_throwsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.unlikePost(null, mockPrincipal));
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getReason()).contains("Invalid project id");
    }

    @Test
    void unlikePost_negativeId_throwsException() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.unlikePost(-10L, mockPrincipal));
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getReason()).contains("Invalid project id");
    }
}