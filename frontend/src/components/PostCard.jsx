/* eslint-disable react/prop-types */
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styles from "@/styles/components/PostCard.module.css";

import ThrashIcon from "@/components/icons/ThrashIcon";
import ArrowIcon from "@/components/icons/ArrowIcon";
import ArrowLeftIcon from "@/components/icons/ArrowLeftIcon";

const PostActionButtons = ({ isFullPage, onLinkToPostsPage, projectId, postId, handleDelete, onLinkToPostPage }) => {
  // navigate to posts page `projects/:projectId/posts`
  if (isFullPage) {
    return (
      <button
        className={styles.arrowButton}
        onClick={() => onLinkToPostsPage()}>
        <ArrowLeftIcon />
      </button>
    )
  }

  // navigate to post page `projects/:projectId/posts/:postId`
  return (
    <>
      <button
        className={styles.deleteButton}
        onClick={() => handleDelete(postId)}>
        <ThrashIcon />
      </button >
      <button
        className={styles.arrowButton}
        onClick={() => onLinkToPostPage(projectId, postId)}>
        <ArrowIcon />
      </button>
    </>
  )
}

const PostCard = ({ post, isFullPage = false }) => {
  const navigate = useNavigate();
  const { projectId } = useParams();

  // TODO - to integrate with comment API
  // eslint-disable-next-line no-unused-vars
  const { id, postOwnerName, postTitle, description, weblinkLink, likes, user } = post;
  const [postImg, setPostImg] = useState(`blob:${weblinkLink}`);

  // TODO - Remove 1 after API data is ready
  const onLinkToPostsPage = () => {
    navigate(`/projects/${projectId}/posts`);
  };

  const handleDelete = (postId) => {
    console.log("post deleted", postId);
  };

  const onLinkToPostPage = (projectId, postId) => {
    navigate(`/projects/${projectId}/posts/${postId}`);
  };

  return (
    <div className={styles.postCard}>
      <div className={styles.postHeader}>
        <div className={styles.avatar} aria-hidden="true">
          {postOwnerName ?? 'AS'}
        </div>

        <div className={styles.postInfo}>
          <h2 className={styles.postName}>
            {postTitle ?? "Untitled Post"}
          </h2>
        </div>

        <div className={styles.postActionButtons}>
          <PostActionButtons
            isFullPage={isFullPage}
            postId={id}
            projectId={projectId}
            onLinkToPostsPage={onLinkToPostsPage}
            handleDelete={handleDelete}
            onLinkToPostPage={onLinkToPostPage}
          />
        </div>
      </div>

      <p className={styles.postContent}>{description}</p>

      <div className={styles.photoContainer}>
        <img
          src={postImg}
          alt="postImg"
          className={styles.photo}
          onError={() => setPostImg("/post_fallback.jpeg")}
        />
      </div>
    </div >
  );
};

export default PostCard;
