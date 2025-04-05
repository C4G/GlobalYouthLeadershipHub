import { useState } from "react";
import { useParams } from "react-router-dom";
import Sidebar from "@/components/Sidebar";
import CreatePost from "@/components/CreatePost";
import PostList from "@/components/PostList";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostsPage.module.css";

const PostsPage = () => {
  const [posts, setPosts] = useState([]);
  const [isModalOpen, setModalOpen] = useState(false);
  const { projectId } = useParams();
  // TODO - to revise once API is up
  console.log("pId", projectId);

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
        {/* TODO - to enable once API is wire up */}
        <h2>Project Name To Be Displayed Here</h2>
        <PostList posts={posts} />
      </main>
    </Container>
  );
};

export default PostsPage;
