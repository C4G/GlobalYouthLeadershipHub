/* eslint-disable react/prop-types */
import { useState } from "react";
import { useParams } from "react-router-dom";
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
          className={`${styles.actionButton} ${isLiked ? styles.liked : ""}`}
          aria-label="like post"
          onClick={handleLikeToggle}
        // disabled={isPending}
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
    </div>
  )
}

const PostPage = () => {
  const queryClient = useQueryClient()
  const { projectId, postId } = useParams();
  const { data: post, isLoading } = useGetProjectPostById(projectId, postId)

  // TODO - to fix on the backend 
  const [isLiked, setIsLiked] = useState(false);
  const { mutate: likePostFunc, } = useLikePostByProjectAndPostId(projectId, postId)

  const [isReplyPost, setIsReplyPost] = useState(false);

  // TODO - to fix on the backend 
  const handleLikeToggle = () => {
    setIsLiked(prev => !prev)
    likePostFunc(null, {
      onSuccess: () => queryClient.invalidateQueries(["projectPost", postId]),
      onError: () => setIsLiked(prev => !prev)
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
          <PostCard isFullPage={true} post={post}>
            <PostActionsSection
              isLiked={isLiked}
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
