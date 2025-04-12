/* eslint-disable react/prop-types */
import { useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Sidebar from "@/components/Sidebar";
import CreatePost from "@/components/CreatePost";
import PostList from "@/components/PostList";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostsPage.module.css";
import CreatePostIcon from "@/components/icons/CreatePostIcon";

const PostsPageHeader = ({ projectName, handleOpenModal, isModalOpen, handleCloseModal, handleCreatePost }) => {
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
          onClose={handleCloseModal}
          onCreate={handleCreatePost}
        />
      )}
    </div>
  )
}

const PostsPage = () => {
  // TODO - to revise once API is up
  const { state } = useLocation();
  const projectName = state?.name ?? "Untitled Project Name";
  const { projectId } = useParams();
  console.log("pId", projectId);

  // Handle Modal Logic
  const [isModalOpen, setModalOpen] = useState(false);
  const handleOpenModal = () => setModalOpen(true)
  const handleCloseModal = () => setModalOpen(false);

  // Handle Posts Logic
  // TODO - to revise once API is up
  const [posts, setPosts] = useState([]);
  const handleCreatePost = (newPost) => {
    setPosts((prev) => [...prev, newPost]);
  };

  return (
    <Container>
      <Sidebar />
      <main className={styles.mainContent}>
        <PostsPageHeader
          projectName={projectName}
          handleOpenModal={handleOpenModal}
          isModalOpen={isModalOpen}
          handleCloseModal={handleCloseModal}
          handleCreatePost={handleCreatePost}
        />
        <PostList posts={posts} />
      </main>
    </Container>
  );
};

export default PostsPage;
