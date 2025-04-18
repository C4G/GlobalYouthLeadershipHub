/* eslint-disable react/prop-types */
import { useNavigate, useParams } from "react-router-dom";
import styles from "@/styles/components/PostCard.module.css";
import ThrashIcon from "@/components/icons/ThrashIcon";
import ArrowIcon from "@/components/icons/ArrowIcon";
import ArrowLeftIcon from "@/components/icons/ArrowLeftIcon";
import { truncateOwnerName, dateStringToLocaleString } from "@/utils/utils"
import { useGetProjectPostImageById } from "@/hooks/posts";

const PostActionButtons = ({ isFullPage, onLinkToPostsPage, projectId, postId, handleDelete, onLinkToPostPage }) => {
  // navigate to posts page `projects/:projectId/posts`
  if (isFullPage) {
    return (
      <button
        className={styles.arrowButton}
        onClick={() => onLinkToPostsPage(projectId)}>
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

const PostCard = ({ post, projectName = "", isFullPage = false, children }) => {
  const navigate = useNavigate();
  const { projectId } = useParams();
  const { id: postId, title, content, postOwner, createdAt, imageUrls } = post;
  const { data: postImageSrc, isLoading: isPostImgLoading } = useGetProjectPostImageById(imageUrls)

  const handleDelete = (postId) => {
    console.log("post deleted", postId);
  };

  const onLinkToPostPage = () => {
    navigate(`/projects/${projectId}/posts/${postId}`,{
      state: { name: projectName },
    } );
  };

  const onLinkToPostsPage = () => {
    navigate(`/projects/${projectId}/posts`, {
      state: { name: projectName },
    });
  };

  return (
    <>
      <div className={styles.postCard}>
        <div className={styles.postHeader}>
          <div className={styles.avatar} aria-hidden="true">
            {truncateOwnerName(postOwner)}
          </div>

          <div className={styles.postInfo}>
            <h2 className={styles.postName}>
              {title ?? "Untitled Post"}
            </h2>
          </div>

          <div className={styles.postActionButtons}>
            <PostActionButtons
              isFullPage={isFullPage}
              postId={postId}
              projectId={projectId}
              handleDelete={handleDelete}
              onLinkToPostPage={onLinkToPostPage}
              onLinkToPostsPage={onLinkToPostsPage}
            />
          </div>
        </div>

        <p className={styles.postContent}>{content}</p>

        <div className={styles.photoContainer}>
          {isPostImgLoading ? <div className={styles.loaderSpinner} /> : postImageSrc.map((img, idx) => {
            return (
              <img
                key={idx}
                src={img}
                alt={`Post Image ${idx}`}
                className={styles.photo}
                // to handle on first render where it might be showing broken image
                onError={(e) => (e.currentTarget.src = "/post_fallback.jpeg ")}
              />
            )
          })}
        </div>

        <p className={styles.postCreatedAt}>
          Last Created: {dateStringToLocaleString(createdAt)}
        </p>
      </div >
      {isFullPage && children}
    </>
  );
};

export default PostCard;
