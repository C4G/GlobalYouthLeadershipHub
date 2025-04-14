package com.legacyinternational.globalyouthleadership.infrastructure.repositories;

import com.legacyinternational.globalyouthleadership.service.post.Post;
import com.legacyinternational.globalyouthleadership.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByProjectIdOrderByCreatedAtDesc(Long projectId);
    List<Post> findAllByAuthorEmailOrderByCreatedAtDesc(String authorEmail);
}
