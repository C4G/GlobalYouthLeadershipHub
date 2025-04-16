/* eslint-disable react/prop-types */
import { useState } from "react";
import { truncateOwnerName } from "@/utils/utils";
import { useAddCommentByProjectAndPostId } from "@/hooks/comments";

import styles from "@/styles/components/Comment.module.css"

const Comment = ({ projectId, postId, comment }) => {
  const [inputReplyComment, setInputReplyComment] = useState("");
  const [isReplyComment, setIsReplyComment] = useState(false)
  const { mutate: addReplyComment } = useAddCommentByProjectAndPostId(projectId, postId)

  const handleReplyComment = () => {
    setIsReplyComment(prev => !prev)
  }

  const onInputReplyCommentChange = (e) => {
    setInputReplyComment(e.target.value)
  }

  const handleSubmitReplyComment = (e) => {
    if (e.key === "Enter" && inputReplyComment.trim() !== "") {
      const commentReplyData = {
        parentCommentId: comment.id,
        content: inputReplyComment
      }

      addReplyComment(
        commentReplyData, {
        onSuccess: () => {
          setInputReplyComment("");
          setIsReplyComment(false);
        }
      })
    }
  }

  return (
    <>
      <div key={comment.id} className={styles.commentWrapper}>
        <div className={styles.commentRow}>
          <div className={styles.commentProfilePic}>
            {truncateOwnerName(comment.commentOwner) ?? "AS"}
          </div>

          <div className={styles.commentContent}>
            <p className={styles.commentOwnerName}>{truncateOwnerName(comment.commentOwner) ?? "AS"}</p>
            <span>says:</span>
            <p className={styles.commentText}>{comment.content}</p>
            <button onClick={handleReplyComment} className={styles.replyCommentButton}>
              {isReplyComment ? "Cancel" : "Reply"}
            </button>
            {isReplyComment && (
              <input
                type="text"
                className={styles.replyCommentInput}
                placeholder="Add a reply to comment"
                value={inputReplyComment}
                onChange={onInputReplyCommentChange}
                onKeyDown={handleSubmitReplyComment}
              />
            )}
          </div>
        </div>

        {comment.replies?.length > 0 && (
          <div className={styles.replyCommentList}>
            {comment.replies.map(reply => {
              return (
                <Comment
                  key={reply.id}
                  projectId={projectId}
                  postId={postId}
                  comment={reply}
                />
              )
            })}
          </div>
        )}

      </div>
    </>
  )
}

export default Comment