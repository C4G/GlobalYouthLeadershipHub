/* eslint-disable react/prop-types */
import { useState } from "react";
import Spinner from "@/components/Spinner";
import Comment from "@/components/Comment";
import { useGetCommentsByProjectAndPostId, useAddCommentByProjectAndPostId } from "@/hooks/comments";

import styles from "./CommentSection.module.css";

const CommentSection = ({ projectId, postId, isReplyPost, setIsReplyPost }) => {
  const [inputComment, setInputComment] = useState("");

  const { data: comments, isLoading } = useGetCommentsByProjectAndPostId(projectId, postId)
  const { mutate: addComment } = useAddCommentByProjectAndPostId(projectId, postId)

  const onInputCommentChange = (e) => {
    setInputComment(e.target.value);
  };

  const handleSubmitComment = (e) => {
    if (e.key === "Enter" && inputComment.trim() !== "") {
      const commentData = { content: inputComment }
      addComment(
        commentData, {
        onSuccess: () => {
          setInputComment("");
          setIsReplyPost(false);
        }
      })
    }
  };

  if (isLoading) {
    return (
      <Spinner text={"Fetching comments..."} />
    )
  }

  return (
    <div className={styles.commentSection}>
      {isReplyPost && (
        <input
          type="text"
          className={styles.commentInput}
          placeholder="Add a comment..."
          value={inputComment}
          onChange={onInputCommentChange}
          onKeyDown={handleSubmitComment}
        />
      )}

      <div className={styles.commentsList}>
        {comments.map(comment => {
          return (
            <Comment
              key={comment.id}
              projectId={projectId}
              postId={postId}
              comment={comment}
            />
          )
        })}
      </div>
    </div>
  );
};

export default CommentSection;
