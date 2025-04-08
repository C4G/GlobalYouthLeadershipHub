import { useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Sidebar from "@/components/Sidebar";
import CreatePost from "@/components/CreatePost";
import PostList from "@/components/PostList";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostsPage.module.css";
import CreatePostIcon from "@/components/icons/CreatePostIcon";

const PostsPage = () => {
  const location = useLocation();
  const projectName = location.state.name;

  // TODO - to revise once API is up
  const { projectId } = useParams();
  console.log("pId", projectId);

  const [posts, setPosts] = useState([]);
  const [isModalOpen, setModalOpen] = useState(false);
  const handleCloseModal = () => {
    setModalOpen(false);
  };

  const handleCreatePost = (newPost) => {
    setPosts((prev) => [...prev, newPost]);
  };

  return (
    <Container>
      <Sidebar setModalOpen={setModalOpen} />
      <main className={styles.mainContent}>
        <div className={styles.headerRow}>
          {/* TODO - to enable once API is wire up */}
          {isModalOpen && (
            <CreatePost
              onClose={handleCloseModal}
              onCreate={handleCreatePost}
            />
          )}
          <div className={styles.actionRow}>
            <button
              onClick={() => {
                setModalOpen(true);
              }}
              className={styles.navListButton}
            >
              <CreatePostIcon /> Create Post
            </button>
          </div>

          <h2>{projectName}</h2>
        </div>
        <PostList posts={posts} />
      </main>
    </Container>
  );
};

export default PostsPage;
