package com.legacyinternational.globalyouthleadership.adapter.web;

import com.legacyinternational.globalyouthleadership.adapter.web.models.*;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.PostImageRepository;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.UserRepository;
import com.legacyinternational.globalyouthleadership.service.post.PostImage;
import com.legacyinternational.globalyouthleadership.service.project.PostServiceImpl;
import com.legacyinternational.globalyouthleadership.service.project.Project;
import com.legacyinternational.globalyouthleadership.service.project.ProjectServiceImpl;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PostServiceImpl postService;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService, PostServiceImpl postService, PostImageRepository postImageRepository, UserRepository userRepository) {
        this.projectService = projectService;
        this.postService = postService;
        this.postImageRepository = postImageRepository;
        this.userRepository = userRepository;
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
            User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

            projectRequest.setProjectOwner(user.getEmail());

            Project project = projectService.createProject(projectRequest);
            ProjectResponse projectResponse = ProjectResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .projectOwner(project.getProjectOwner().getFullName())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/projects/%s/image", project.getId()))
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
        List<PostResponse> posts = postService.getPostsByProject(id);
        try {
            Project project = projectById.get();
            return ResponseEntity.ok(ProjectResponse.builder()
                            .id(project.getId())
                    .name(project.getName())
                    .projectOwner(project.getProjectOwner().getFullName())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/projects/%s/image", project.getId()))
                    .postCount(posts.size())
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
                    .projectOwner(project.getProjectOwner().getFullName())
                    .description(project.getDescription())
                    .projectImageUrl(String.format("/projects/%s/image", project.getId()))
                    .postCount(postService.getPostsByProject(project.getId()).size())
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

    @PostMapping(value = "/{projectId}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(@PathVariable Long projectId,
                                                   @RequestParam("title") String title,
                                                   @RequestParam("content") String content,
                                                   @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                                   Principal principal) {
        if (Objects.isNull(projectId) || projectId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        if (Objects.isNull(title) || title.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid title");
        }
        if (Objects.isNull(content) || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid content");
        }
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String type = image.getContentType();
                if (!List.of("image/jpeg", "image/png").contains(type)) {
                    throw new IllegalArgumentException("Only JPEG and PNG formats are supported");
                }
            }
        }
        return ResponseEntity.ok(postService.createPost(projectId, content, images, title, principal.getName()));
    }

    @GetMapping("/{projectId}/posts")
    public ResponseEntity<List<PostResponse>> getPostsByProject(@PathVariable Long projectId) {
        if (Objects.isNull(projectId) || projectId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        return ResponseEntity.ok(postService.getPostsByProject(projectId));
    }

    @GetMapping("/{projectId}/posts/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetails(@PathVariable Long projectId, @PathVariable Long postId) {
        if (Objects.isNull(postId) || postId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        return ResponseEntity.ok(postService.getPostDetails(projectId, postId));
    }

    @GetMapping("/{projectId}/posts/{postId}/images/{imageId}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long projectId, @PathVariable Long postId, @PathVariable Long imageId) {
        if (Objects.isNull(projectId) || projectId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        if (Objects.isNull(postId) || postId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid post id");
        }
        if (Objects.isNull(imageId) || imageId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image id");
        }

        Optional<Project> projectById = projectService.getProjectById(projectId);
        if (projectById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }

        List<PostResponse> postsByProject = postService.getPostsByProject(projectId);
        if (postsByProject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        Optional<PostImage> image = postImageRepository.findById(imageId);
        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.get().getFileName() + "\"")
                .body(image.get().getFileData());

    }

    @PostMapping("/{projectId}/posts/{postId}/like")
    public ResponseEntity<ApiResponse> likePost(@PathVariable Long postId, Principal principal) {
        if (Objects.isNull(postId) || postId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        postService.likePost(postId, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Post liked successfully"));
    }

    @PostMapping("/{projectId}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long postId,
                                                  @RequestBody CommentRequest request,
                                                  Principal principal) {
        if (Objects.isNull(postId) || postId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid postId id");
        }

        postService.addComment(postId, principal.getName(), request);
        return ResponseEntity.ok(new ApiResponse("Comment added successfully"));
    }

    @GetMapping("/{projectId}/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        if (Objects.isNull(postId) || postId.intValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid postId id");
        }
        return ResponseEntity.ok(postService.getComments(postId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProjectResponse>> getProjectsByCurrentUser(Principal principal) {
        String userEmail = principal.getName();
        List<Project> userProjects = projectService.getProjectsByCreatedBy(userEmail);

        List<ProjectResponse> projectResponses = userProjects.stream()
                .map(project -> ProjectResponse.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .projectOwner(project.getProjectOwner().getFullName())
                        .description(project.getDescription())
                        .projectImageUrl(String.format("/projects/%s/image", project.getId()))
                        .postCount(postService.getPostsByProject(project.getId()).size())
                        .createdDate(String.valueOf(project.getCreatedAt()))
                        .createdBy(project.getCreatedBy())
                        .lastModifiedBy(project.getUpdatedBy())
                        .lastModifiedDate(String.valueOf(project.getUpdatedAt()))
                        .build())
                .toList();

        return ResponseEntity.ok(projectResponses);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<PostResponse>> getPostsByCurrentUser(Principal principal) {
        String authorEmail = principal.getName();
        List<PostResponse> userPosts = postService.getPostsByUser(authorEmail);
        return ResponseEntity.ok(userPosts);
    }
}
