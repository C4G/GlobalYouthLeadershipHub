import { useState } from "react";
import { useParams } from "react-router-dom";
import PostCard from "@/components/PostCard";
import Sidebar from "@/components/Sidebar";
import Container from "@/components/Container";
import HeartIcon from "@/components/icons/HeartIcon";
import LikeIcon from "@/components/icons/LikeIcon";
import ReplyIcon from "@/components/icons/ReplyIcon";
import CommentSection from "@/components/CommentSection";
import styles from "@/styles/pages/PostPage.module.css";

// TODO - Remove after API data is ready
const dummyPosts = [
  {
    id: 1,
    userId: 1,
    postOwnerName: "MJ",
    postTitle: "Michael Jordan is the GOAT",
    description: "This is a sample post",
    weblinkLink: "https://example.com/image1.jpg",
    likes: 3,
    user: "mj23",
  },
];

const PostPage = () => {
  const { projectId, postId } = useParams();
  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(0);
  const [commentCounts, setCommentCounts] = useState(0);
  const [isReplying, setIsReplying] = useState(false);

  const handleLikeToggle = () => {
    setLikeCount((prevCount) => (isLiked ? prevCount - 1 : prevCount + 1));
    setIsLiked(!isLiked);
  };

  const handleReplyClick = () => {
    setIsReplying((isReplying) => !isReplying);
  };

  const dummyPost = dummyPosts.find((p) => String(p.id) === postId);

  if (!dummyPost) return <div>Post Not Found</div>;

  const dummyFullPostData = {
    ...dummyPost,
    projectId: projectId,
  };

  return (
    <Container>
      <Sidebar />
      <main className={styles.mainContent}>
        <PostCard isFullPage={true} post={dummyFullPostData} />

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
              className={`${styles.actionButton} ${isLiked ? styles.liked : ""
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
        <CommentSection
          user={dummyFullPostData.user}
          isReplying={isReplying}
          setIsReplying={setIsReplying}
          setCommentCounts={setCommentCounts}
        />
      </main>
    </Container>
  );
};

export default PostPage;
