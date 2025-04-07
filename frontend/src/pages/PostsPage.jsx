import { useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Sidebar from "@/components/Sidebar";
import CreatePost from "@/components/CreatePost";
import PostList from "@/components/PostList";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostsPage.module.css";

const PostsPage = () => {
  const location = useLocation()
  const projectName = location.state.name

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
      <Sidebar isPostsPage={true} setModalOpen={setModalOpen} />
      <main className={styles.mainContent}>
        {/* TODO - to enable once API is wire up */}
        {isModalOpen && <CreatePost onClose={handleCloseModal} onCreate={handleCreatePost} />}
        <h2>{projectName}</h2>
        <PostList posts={posts} />
      </main>
    </Container>
  );
};

export default PostsPage;
