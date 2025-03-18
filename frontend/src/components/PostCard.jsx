import { useState } from "react";
import styles from "@/styles/components/PostCard.module.css";
import HeartIcon from "@/components/icons/HeartIcon";
import LikeIcon from "@/components/icons/LikeIcon";
import ReplyIcon from "@/components/icons/ReplyIcon";

// eslint-disable-next-line react/prop-types
const PostCard = ({
  initials,
  projectName,
  content,
  imageSrc,
  likes,
  comments,
}) => {
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(likes);

  const handleLikeToggle = () => {
    setLikeCount((prevCount) => (isLiked ? prevCount - 1 : prevCount + 1));
    setIsLiked(!isLiked);
  };

  return (
    <div className={styles.postCard}>
      <div className={styles.postHeader}>
        <div className={styles.avatar} aria-hidden="true">
          {initials}
        </div>
        <div className={styles.postInfo}>
          <h2 className={styles.projectName}>{projectName}</h2>
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
          {comments} comments
        </p>
        <div className={styles.actions}>
          <button
            className={`${styles.actionButton} ${isLiked ? styles.liked : ""}`}
            aria-label="like post"
            onClick={handleLikeToggle}
          >
            <LikeIcon filled={isLiked} /> Like
          </button>
          <button className={styles.actionButton} aria-label="reply on post">
            <ReplyIcon /> Reply
          </button>
        </div>
      </div>
    </div>
  );
};

export default PostCard;
