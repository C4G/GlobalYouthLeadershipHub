package com.legacyinternational.globalyouthleadership.infrastructure.repositories;

import com.legacyinternational.globalyouthleadership.service.post.PostImage;
import com.legacyinternational.globalyouthleadership.service.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserEmail(Long postId, String userEmail);
    Optional<PostLike> getPostLikeByPostIdAndUserEmail(Long postId, String userEmail);

}
