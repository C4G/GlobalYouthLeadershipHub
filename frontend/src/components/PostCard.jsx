/* eslint-disable react/prop-types */
import { useState } from "react";
import styles from "@/styles/components/PostCard.module.css";
import HeartIcon from "@/components/icons/HeartIcon";
import LikeIcon from "@/components/icons/LikeIcon";
import ReplyIcon from "@/components/icons/ReplyIcon";
import CommentSection from "@/components/CommentSection";

const PostCard = ({
  initials,
  postName,
  content,
  imageSrc,
  likes,
  user,
}) => {
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(likes);
  const [commentCounts, setCommentCounts] = useState(0);
  const [isReplying, setIsReplying] = useState(false);

  const handleLikeToggle = () => {
    setLikeCount((prevCount) => (isLiked ? prevCount - 1 : prevCount + 1));
    setIsLiked(!isLiked);
  };

  const handleReplyClick = () => {
    setIsReplying((isReplying) => !isReplying);
  };

  return (
    <div className={styles.postCard}>
      <div className={styles.postHeader}>
        <div className={styles.avatar} aria-hidden="true">
          {initials}
        </div>
        <div className={styles.postInfo}>
          <h2 className={styles.postName}>{postName}</h2>
        </div>
      </div>

      <p className={styles.postContent}>{content}</p>

      <div className={styles.photoContainer}>
        <img src={imageSrc} alt="Image Not Found" className={styles.photo} />
      </div>

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
            className={`${styles.actionButton} ${isLiked ? styles.liked : ""}`}
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
