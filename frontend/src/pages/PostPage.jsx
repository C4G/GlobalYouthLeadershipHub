/* eslint-disable react/prop-types */
import { useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import { useQueryClient } from "@tanstack/react-query";

import PostCard from "@/components/PostCard";
import Sidebar from "@/components/Sidebar";
import Container from "@/components/Container";
import HeartIcon from "@/components/icons/HeartIcon";
import LikeIcon from "@/components/icons/LikeIcon";
import ReplyIcon from "@/components/icons/ReplyIcon";
import Spinner from "@/components/Spinner";
import CommentSection from "@/components/CommentSection";
import { useGetProjectPostById, useLikePostByProjectAndPostId } from "@/hooks/posts";


import styles from "@/styles/pages/PostPage.module.css";

const PostActionsSection = ({ isLiked, likeCount, commentCount, handleLikeToggle, handleReplyPost }) => {
  return (
    <div className={styles.postFooter}>
      <div className={styles.reactions}>
        <div aria-hidden="true" className={styles.reactionsSvg}>
          <HeartIcon /> <span>{likeCount}</span>
        </div>
      </div>

      <p className={styles.postStats} aria-hidden="true">
        {commentCount} comments
      </p>

      <div className={styles.actions}>
        <button
          className={styles.actionButton}
          aria-label="like post"
          onClick={handleLikeToggle}
        >
          <LikeIcon filled={isLiked} /> Like
        </button>
        <button
          className={styles.actionButton}
          aria-label="reply on post"
          onClick={handleReplyPost}
        >
          <ReplyIcon /> Reply
        </button>
      </div>
    </div >
  )
}

const PostPage = () => {
  const queryClient = useQueryClient()
  const { projectId, postId } = useParams();
  const { data: post, isLoading } = useGetProjectPostById(projectId, postId)
  const { mutate: likePostFunc } = useLikePostByProjectAndPostId(projectId, postId)

  const [isReplyPost, setIsReplyPost] = useState(false);

  const { state } = useLocation();
  const projectName = state?.name ?? "Untitled Project";  

  const handleLikeToggle = () => {
    const isCurrentlyLike = post?.likedByLoggedInUser
    const endpoint = !isCurrentlyLike ? 'like' : 'unlike'
    likePostFunc(endpoint, {
      onSuccess: () => queryClient.invalidateQueries(["projectPost", postId]),
    })
  };

  const handleReplyPost = () => {
    setIsReplyPost((prev) => !prev);
  };

  return (
    <Container>
      <Sidebar />
      <main className={styles.mainContent}>
        {isLoading || !post ?
          <Spinner text={"Fetching post..."} /> :
          <PostCard isFullPage={true} post={post} projectName={projectName}>
            <PostActionsSection
              isLiked={post.likedByLoggedInUser}
              likeCount={post.likeCount}
              commentCount={post.commentCount}
              handleLikeToggle={handleLikeToggle}
              handleReplyPost={handleReplyPost}
            />
            <CommentSection
              projectId={projectId}
              postId={postId}
              user={post.postOwner}
              isReplyPost={isReplyPost}
              setIsReplyPost={setIsReplyPost}
              comments={post.comments}
            />
          </PostCard>
        }
      </main>
    </Container>
  );
};

export default PostPage;
