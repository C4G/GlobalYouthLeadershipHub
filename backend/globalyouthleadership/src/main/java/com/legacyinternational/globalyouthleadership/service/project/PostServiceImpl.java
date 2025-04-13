package com.legacyinternational.globalyouthleadership.service.project;

import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentRequest;
import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostDetailResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostResponse;
import com.legacyinternational.globalyouthleadership.infrastructure.repositories.*;
import com.legacyinternational.globalyouthleadership.service.PostService;
import com.legacyinternational.globalyouthleadership.service.post.Post;
import com.legacyinternational.globalyouthleadership.service.post.PostComment;
import com.legacyinternational.globalyouthleadership.service.post.PostImage;
import com.legacyinternational.globalyouthleadership.service.post.PostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PostImageRepository postImageRepository,
                           PostLikeRepository postLikeRepository,
                           PostCommentRepository postCommentRepository,
                           ProjectRepository projectRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.postLikeRepository = postLikeRepository;
        this.postCommentRepository = postCommentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public PostResponse createPost(Long projectId, String content, List<MultipartFile> images, String title, String authorEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        Post postToCreate = Post.builder()
                .project(project)
                .title(title)
                .content(content)
                .authorEmail(authorEmail)
                .createdAt(LocalDateTime.now())
                .createdBy(authorEmail)
                .updatedAt(LocalDateTime.now())
                .updatedBy(authorEmail)
                .build();


        Post savedPost = postRepository.save(postToCreate);

        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                try {
                    PostImage postImage = PostImage.builder()
                            .post(savedPost)
                            .fileName(image.getOriginalFilename())
                            .fileType(image.getContentType())
                            .fileData(image.getBytes())
                            .createdAt(LocalDateTime.now())
                            .createdBy(authorEmail)
                            .updatedAt(LocalDateTime.now())
                            .updatedBy(authorEmail)
                            .build();
                    postImageRepository.save(postImage);
                    imageUrls.add(String.format("/projects/%s/posts/%s/images/%d", project.getId(), savedPost.getId(), postImage.getId()));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Image upload failed");
                }
            }
        }

        return PostResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .authorEmail(savedPost.getAuthorEmail())
                .createdAt(savedPost.getCreatedAt())
                .imageUrls(imageUrls)
                .likeCount(0)
                .build();
    }

    @Override
    public List<PostResponse> getPostsByProject(Long projectId) {
        return postRepository.findAllByProjectIdOrderByCreatedAtDesc(projectId).stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .authorEmail(post.getAuthorEmail())
                        .createdAt(post.getCreatedAt())
                        .likeCount((int) postLikeRepository.findAll().stream()
                                .filter(like -> like.getPostId().equals(post.getId()))
                                .count())
                        .commentCount(postCommentRepository.countAllByPostId(post.getId()))
                        .imageUrls(postImageRepository.findByPostId(post.getId()).stream()
                                .map(image -> String.format("/api/posts/%d/images/%d", post.getId(), image.getId()))
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public PostDetailResponse getPostDetails(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        List<String> imageUrls = postImageRepository.findByPostId(postId).stream()
                .map(image -> String.format("/api/posts/%d/images/%d", postId, image.getId()))
                .toList();

        List<CommentResponse> comments = getNestedComments(postCommentRepository.findByPostIdOrderByCreatedAtAsc(postId), null);

        int likeCount = (int) postLikeRepository.findAll().stream()
                .filter(like -> like.getPostId().equals(postId)).count();

        int commentCount = postCommentRepository.countAllByPostId(post.getId());

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorEmail(post.getAuthorEmail())
                .createdAt(post.getCreatedAt())
                .imageUrls(imageUrls)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .comments(comments)
                .build();
    }

    private List<CommentResponse> getNestedComments(List<PostComment> allComments, Long parentId) {
        return allComments.stream()
                .filter(c -> (c.getParentComment() == null && parentId == null)
                        || (c.getParentComment() != null && c.getParentComment().getId().equals(parentId)))
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .userEmail(c.getUserEmail())
                        .createdAt(c.getCreatedAt())
                        .replies(getNestedComments(allComments, c.getId()))
                        .build())
                .toList();
    }

    @Override
    public void likePost(Long postId, String userEmail) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        // Already liked, idempotent
        if (postLikeRepository.existsByPostIdAndUserEmail(postId, userEmail)) {
            return;
        }

        PostLike like = new PostLike();
        like.setPostId(postId);
        like.setUserEmail(userEmail);
        like.setLikedAt(LocalDateTime.now());
        postLikeRepository.save(like);
    }

    @Override
    public void addComment(Long postId, String userEmail, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        PostComment comment = new PostComment();
        comment.setPost(post);
        comment.setUserEmail(userEmail);
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCreatedBy(userEmail);
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setUpdatedBy(userEmail);

        if (request.getParentCommentId() != null) {
            PostComment parent = postCommentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent comment not found"));
            comment.setParentComment(parent);
        }

        postCommentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getComments(Long postId) {
        List<PostComment> comments = postCommentRepository.findByPostIdOrderByCreatedAtAsc(postId);
        return getNestedComments(comments, null);
    }
}
