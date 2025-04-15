/* eslint-disable react/prop-types */
import { useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Sidebar from "@/components/Sidebar";
import CreatePost from "@/components/CreatePost";
import PostList from "@/components/PostList";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostsPage.module.css";
import CreatePostIcon from "@/components/icons/CreatePostIcon";
import { useGetAllPosts } from "@/hooks/posts";

const PostsPageHeader = ({ projectId, projectName, handleOpenModal, isModalOpen, handleCloseModal }) => {
  return (
    <div className={styles.headerPostsRow}>
      <h2 className={styles.headerPostsTitle}>{projectName}</h2>
      <div className={styles.actionPostsRow}>
        <button
          onClick={handleOpenModal}
          className={styles.createPostButton}
        >
          <CreatePostIcon /> Create Post
        </button>
      </div>
      {isModalOpen && (
        <CreatePost
          projectId={projectId}
          onClose={handleCloseModal}
        />
      )}
    </div>
  )
}

const PostsPage = () => {
  const { state } = useLocation();
  const projectName = state?.name ?? "Untitled Project Name";
  const { projectId } = useParams();
  const { data: posts, isLoading, refetch: refetchAllPosts } = useGetAllPosts(projectId)

  // Handle Modal Logic
  const [isModalOpen, setModalOpen] = useState(false);
  const handleOpenModal = () => setModalOpen(true)
  const handleCloseModal = () => {
    setModalOpen(false)
    refetchAllPosts()
  };

  return (
    <Container>
      <Sidebar />
      <main className={styles.mainContent}>
        <PostsPageHeader
          projectId={projectId}
          projectName={projectName}
          handleOpenModal={handleOpenModal}
          isModalOpen={isModalOpen}
          handleCloseModal={handleCloseModal}
        />
        <PostList posts={posts} isLoading={isLoading} />
      </main>
    </Container>
  );
};

export default PostsPage;
