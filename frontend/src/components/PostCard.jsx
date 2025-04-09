/* eslint-disable react/prop-types */
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "@/styles/components/PostCard.module.css";
import HeartIcon from "@/components/icons/HeartIcon";
import LikeIcon from "@/components/icons/LikeIcon";
import ReplyIcon from "@/components/icons/ReplyIcon";
import CommentSection from "@/components/CommentSection";
import ThrashIcon from "@/components/icons/ThrashIcon";
import ArrowIcon from "@/components/icons/ArrowIcon";
import ArrowLeftIcon from "@/components/icons/ArrowLeftIcon";

const PostCard = ({ post, isFullPage = false }) => {
  // TODO - to integrate with comment API
  // eslint-disable-next-line no-unused-vars
  const navigate = useNavigate();
  const { id, initials, name, description, weblinkLink, likes, user } = post;
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(0);
  const [commentCounts, setCommentCounts] = useState(0);
  const [isReplying, setIsReplying] = useState(false);

  const [postImg, setPostImg] = useState(`blob:${weblinkLink}`);

  const handleLikeToggle = () => {
    setLikeCount((prevCount) => (isLiked ? prevCount - 1 : prevCount + 1));
    setIsLiked(!isLiked);
  };

  const handleReplyClick = () => {
    setIsReplying((isReplying) => !isReplying);
  };

  //  // TODO - Remove 1 after API data is ready
  const onLinkToPost = (id, postId = 1) => {
    if (!isFullPage) {
      navigate(`/projects/${id}/posts/${postId}`);
    }
  };

  const onLinkToPosts = (id) => {
    if (isFullPage) {
      navigate(`/projects/${id}/posts`);
    }
  };

  const handleDelete = (id) => {
    console.log("post deleted", id);
  };

  return (
    <div
      className={`${styles.postCard} ${
        isFullPage ? styles.staticCard : styles.clickableCard
      }`}
    >
      <div className={styles.postHeader}>
        <div className={styles.avatar} aria-hidden="true">
          {initials ?? "MJ"}
        </div>
        <div className={styles.postInfo}>
          {/* TODO - to remove after Demo */}
          <h2 className={styles.postName}>
            {name ?? "Post Name to Be Displayed"}
          </h2>
        </div>

        {isFullPage ? (
          <div className={styles.postActionButtons}>
            <button
              className={styles.arrowButton}
              onClick={() => onLinkToPosts(id)}
            >
              <ArrowLeftIcon />
            </button>
          </div>
        ) : (
          <div className={styles.postActionButtons}>
            <button
              className={styles.deleteButton}
              onClick={() => handleDelete(id)}
            >
              <ThrashIcon />
            </button>
            <button
              className={styles.arrowButton}
              onClick={() => onLinkToPost(id, post.postId)}
            >
              <ArrowIcon />
            </button>
          </div>
        )}
      </div>

      <p className={styles.postContent}>{description}</p>

      <div className={styles.photoContainer}>
        <img
          src={postImg}
          alt="Post Not Found"
          className={styles.photo}
          onError={() => setPostImg("/post_fallback.jpeg")}
        />
      </div>

      {isFullPage ? (
        <div className={styles.postFooter}>
          <div className={styles.reactions}>
            <div aria-hidden="true" className={styles.reactionsSvg}>
              <HeartIcon /> <span>{likeCount}</span>
            </div>
          </div>
          <p className={styles.postStats} aria-hidden="true">
            {commentCounts} comments
          </p>
          <div className={styles.actions}>
            <button
              className={`${styles.actionButton} ${
                isLiked ? styles.liked : ""
              }`}
              aria-label="like post"
              onClick={handleLikeToggle}
            >
              <LikeIcon filled={isLiked} /> Like
            </button>
            <button
              className={styles.actionButton}
              aria-label="reply on post"
              onClick={handleReplyClick}
            >
              <ReplyIcon /> Reply
            </button>
          </div>
        </div>
      ) : null}

      <CommentSection
        user={user}
        isReplying={isReplying}
        setIsReplying={setIsReplying}
        setCommentCounts={setCommentCounts}
      />
    </div>
  );
};

export default PostCard;
