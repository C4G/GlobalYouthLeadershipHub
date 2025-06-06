package com.legacyinternational.globalyouthleadership.service;

import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentRequest;
import com.legacyinternational.globalyouthleadership.adapter.web.models.CommentResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostDetailResponse;
import com.legacyinternational.globalyouthleadership.adapter.web.models.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {
    PostResponse createPost(Long projectId, String content, List<MultipartFile> images, String title, String authorEmail);
    List<PostResponse> getPostsByProject(Long projectId, String userEmail);
    PostDetailResponse getPostDetails(Long projectId, Long postId, String userEmail);
    void likePost(Long postId, String userEmail);
    void unlikePost(Long postId, String userEmail);
    void addComment(Long postId, String userEmail, CommentRequest request);
    List<CommentResponse> getComments(Long postId);
}
