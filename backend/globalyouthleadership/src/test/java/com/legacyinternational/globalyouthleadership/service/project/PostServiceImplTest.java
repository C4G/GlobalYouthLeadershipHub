package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentRequest;
import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostDetailResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostResponse;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.*;
import com.legacyinternational.globalyouthleadership.service.post.Post;
import com.legacyinternational.globalyouthleadership.service.post.PostComment;
import com.legacyinternational.globalyouthleadership.service.post.PostImage;
import com.legacyinternational.globalyouthleadership.service.post.PostLike;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @Mock private ProjectRepository projectRepository;
    @Mock private PostRepository postRepository;
    @Mock private PostImageRepository postImageRepository;
    @Mock private PostLikeRepository postLikeRepository;
    @Mock private UserRepository userRepository;
    @Mock
    private PostCommentRepository postCommentRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(User.builder().firstName("test").lastName("test").email("test@gmail.com").build()));
    }

    @Test
    void createPost_success_withImages() throws IOException {
        Long projectId = 1L;
        String content = "Post content";
        String title = "Title";
        String author = "user@example.com";

        Project project = new Project();
        project.setId(projectId);

        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("file.png");
        when(image.getContentType()).thenReturn("image/png");
        when(image.getBytes()).thenReturn("data".getBytes());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(postRepository.save(any())).thenAnswer(i -> {
            Post p = i.getArgument(0);
            p.setId(10L);
            return p;
        });

        when(postImageRepository.save(any())).thenAnswer(i -> {
            PostImage pi = i.getArgument(0);
            pi.setId(20L);
            return pi;
        });

        PostResponse result = postService.createPost(projectId, content, List.of(image), title, author);

        assertThat(result).isNotNull();
        assertThat(result.getImageUrls().size()).isEqualTo(1);
    }

    @Test
    void createPost_projectNotFound_throwsException() {
        when(projectRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> postService.createPost(1L, "content", null, "title", "user"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Project not found");
    }

    @Test
    void createPost_imageUploadFails_throwsException() throws IOException {
        Project project = new Project();
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));

        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("x.png");
        when(image.getContentType()).thenReturn("image/png");
        when(image.getBytes()).thenThrow(new IOException("Fail"));

        assertThatThrownBy(() -> postService.createPost(1L, "c", List.of(image), "t", "e"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Image upload failed");
    }

    @Test
    void getPostsByProject_success() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("title");
        post.setContent("c");
        post.setPostOwner(User.builder().firstName("test").lastName("test").email("test@gmail.com").build());
        post.setCreatedAt(LocalDateTime.now());

        when(postRepository.findAllByProjectIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(post));
        when(postLikeRepository.findAll()).thenReturn(Collections.emptyList());
        when(postCommentRepository.countAllByPostId(1L)).thenReturn(0);
        when(postImageRepository.findByPostId(1L)).thenReturn(Collections.emptyList());

        List<PostResponse> result = postService.getPostsByProject(1L, "test1@gmail.com");

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getPostDetails_success() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("title");
        post.setContent("c");
        post.setPostOwner(User.builder().firstName("test").lastName("test").email("test@gmail.com").build());
        post.setCreatedAt(LocalDateTime.now());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postImageRepository.findByPostId(1L)).thenReturn(Collections.emptyList());
        when(postCommentRepository.findByPostIdOrderByCreatedAtAsc(1L)).thenReturn(Collections.emptyList());
        when(postLikeRepository.findAll()).thenReturn(Collections.emptyList());
        when(postLikeRepository.getPostLikeByPostIdAndUserEmail(eq(1L), eq("test1@gmail.com"))).thenReturn(Optional.empty());
        when(postCommentRepository.countAllByPostId(1L)).thenReturn(0);

        PostDetailResponse detail = postService.getPostDetails(1L,1L, "test1@gmail.com");

        assertThat(detail).isNotNull();
        assertThat(detail.getId()).isEqualTo(1L);
        assertThat(detail.getTitle()).isEqualTo("title");
        assertThat(detail.getContent()).isEqualTo("c");
        assertThat(detail.getPostOwner()).isEqualTo("test test");
        assertThat(detail.isLikedByLoggedInUser()).isFalse();
    }

    @Test
    void likePost_success() {
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postLikeRepository.existsByPostIdAndUserEmail(1L, "a")).thenReturn(false);

        postService.likePost(1L, "a");

        verify(postLikeRepository).save(any());
    }

    @Test
    void likePost_alreadyLiked_noop() {
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postLikeRepository.existsByPostIdAndUserEmail(1L, "a")).thenReturn(true);

        postService.likePost(1L, "a");

        verify(postLikeRepository, never()).save(any());
    }

    @Test
    void likePost_postNotFound_throwsException() {
        when(postRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> postService.likePost(1L, "a"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Post not found");
    }

    @Test
    void addComment_topLevel() {
        Post post = new Post();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        CommentRequest req = new CommentRequest();
        req.setContent("Nice!");

        postService.addComment(1L, "me", req);

        verify(postCommentRepository).save(any());
    }

    @Test
    void addComment_withParent() {
        Post post = new Post();
        PostComment parent = new PostComment();
        parent.setId(99L);

        CommentRequest req = new CommentRequest();
        req.setContent("reply");
        req.setParentCommentId(99L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postCommentRepository.findById(99L)).thenReturn(Optional.of(parent));

        postService.addComment(1L, "me", req);

        verify(postCommentRepository).save(any());
    }

    @Test
    void addComment_parentNotFound_throws() {
        Post post = new Post();
        CommentRequest req = new CommentRequest();
        req.setContent("reply");
        req.setParentCommentId(123L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postCommentRepository.findById(123L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.addComment(1L, "me", req))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Parent comment not found");
    }

    @Test
    void getComments_shouldReturnNested() {
        PostComment comment = new PostComment();
        comment.setId(1L);
        comment.setContent("hello");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPostOwner(User.builder().firstName("test").lastName("test").email("test@gmail.com").build());

        when(postCommentRepository.findByPostIdOrderByCreatedAtAsc(1L)).thenReturn(List.of(comment));

        List<CommentResponse> result = postService.getComments(1L);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void unlikePost_postNotFound_throwsNotFound() {
        Long postId = 42L;
        String userEmail = "testuser@example.com";
        when(postRepository.existsById(postId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                postService.unlikePost(postId, userEmail));

        assertEquals("Post not found", ex.getReason());
    }

    @Test
    void unlikePost_postNotLiked_throwsBadRequest() {
        Long postId = 42L;
        String userEmail = "testuser@example.com";
        when(postRepository.existsById(postId)).thenReturn(true);
        when(postLikeRepository.existsByPostIdAndUserEmail(postId, userEmail)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                postService.unlikePost(postId, userEmail));

        assertEquals("Post is not liked by User", ex.getReason());
    }

    @Test
    void unlikePost_postLiked_deletesLikeEntry() {
        Long postId = 42L;
        String userEmail = "testuser@example.com";
        PostLike like = PostLike.builder().postId(postId).userEmail(userEmail).build();

        when(postRepository.existsById(postId)).thenReturn(true);
        when(postLikeRepository.existsByPostIdAndUserEmail(postId, userEmail)).thenReturn(true);
        when(postLikeRepository.getPostLikeByPostIdAndUserEmail(postId, userEmail))
                .thenReturn(Optional.of(like));

        postService.unlikePost(postId, userEmail);

        verify(postLikeRepository).delete(like);
    }

    @Test
    void unlikePost_likeExistsButEntryEmpty_nothingDeleted() {
        Long postId = 42L;
        String userEmail = "testuser@example.com";
        when(postRepository.existsById(postId)).thenReturn(true);
        when(postLikeRepository.existsByPostIdAndUserEmail(postId, userEmail)).thenReturn(true);
        when(postLikeRepository.getPostLikeByPostIdAndUserEmail(postId, userEmail)).thenReturn(Optional.empty());

        postService.unlikePost(postId, userEmail);

        // Should not call delete
        verify(postLikeRepository, never()).delete(any());
    }

    @Test
    void getPostsByUser_returnsMappedPosts() {
        String authorEmail = "test@gmail.com";
        Long postId = 1L;
        Long projectId = 99L;

        User author = User.builder().firstName("Test").lastName("User").email(authorEmail).build();
        Project project = new Project(); project.setId(projectId);

        Post post = Post.builder()
                .id(postId)
                .title("Post Title")
                .content("Post Content")
                .postOwner(author)
                .project(project)
                .createdAt(LocalDateTime.now())
                .build();

        PostLike like = PostLike.builder().postId(postId).userEmail(authorEmail).build();

        PostImage image = PostImage.builder().id(10L).post(post).build();

        when(postRepository.findAllByPostOwner_EmailOrderByCreatedAtDesc(authorEmail)).thenReturn(List.of(post));
        when(postLikeRepository.existsByPostIdAndUserEmail(postId, authorEmail)).thenReturn(true);
        when(postLikeRepository.findAll()).thenReturn(List.of(like));
        when(postCommentRepository.countAllByPostId(postId)).thenReturn(2);
        when(postImageRepository.findByPostId(postId)).thenReturn(List.of(image));

        List<PostResponse> result = postService.getPostsByUser(authorEmail);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        PostResponse response = result.get(0);
        assertThat(response.getId()).isEqualTo(postId);
        assertThat(response.getTitle()).isEqualTo("Post Title");
        assertThat(response.getContent()).isEqualTo("Post Content");
        assertThat(response.getPostOwner()).isEqualTo("Test User");
        assertThat(response.isLikedByLoggedInUser()).isTrue();
        assertThat(response.getLikeCount()).isEqualTo(1);
        assertThat(response.getCommentCount()).isEqualTo(2);
        assertThat(response.getImageUrls().get(0)).isEqualTo("/projects/99/posts/1/images/10");
    }

    @Test
    void getPostsByUser_noPosts_returnsEmptyList() {
        when(postRepository.findAllByPostOwner_EmailOrderByCreatedAtDesc("nobody@gmail.com"))
                .thenReturn(Collections.emptyList());

        List<PostResponse> result = postService.getPostsByUser("nobody@gmail.com");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }
}