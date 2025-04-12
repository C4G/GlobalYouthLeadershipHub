/* eslint-disable react/prop-types */
import { useState } from "react";
import styles from "@/styles/components/CommentSection.module.css";

const CommentSection = ({
  user,
  isReplying,
  setIsReplying,
  setCommentCounts,
}) => {
  const [comments, setComments] = useState([]);
  const [inputValue, setInputValue] = useState("");

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && inputValue.trim() !== "") {
      setComments((prevComments) => [
        ...prevComments,
        { id: prevComments.length, text: inputValue, user },
      ]);
      setInputValue("");
      setIsReplying(false);
      setCommentCounts((prev) => prev + 1);
    }
  };

  return (
    <div className={styles.commentSection}>
      {isReplying && (
        <input
          type="text"
          className={styles.commentInput}
          placeholder="Add a comment..."
          value={inputValue}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
        />
      )}
      <div className={styles.commentsList}>
        {comments.map((comment) => (
          <div key={comment.id} className={styles.comment}>
            <div className={styles.profilePic}>
              {comment.user?.name?.slice(0, 2).toUpperCase() || "ME"}
            </div>

            <div className={styles.commentContent}>
              <p className={styles.userName}>{comment.user?.name || "ME"}</p>
              <span>says:</span>
              <p className={styles.commentText}>{comment.text}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CommentSection;
