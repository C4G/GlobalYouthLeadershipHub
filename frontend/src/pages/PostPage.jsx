import { useParams } from "react-router-dom";
import PostCard from "@/components/PostCard";
import Sidebar from "@/components/Sidebar";
import Container from "@/components/Container";
import styles from "@/styles/pages/PostPage.module.css";

// TODO - Remove after API data is ready
const dummyPosts = [
  {
    id: "1",
    initials: "MJ",
    name: "Michael Jordan",
    description: "This is a sample post",
    weblinkLink: "https://example.com/image1.jpg",
    likes: 3,
    user: "mj23",
  },
];

const PostPage = () => {
  const { projectId, postId } = useParams();

  const dummyPost = dummyPosts.find((p) => String(p.id) === postId);

  if (!dummyPost) return <div>Post Not Found</div>;

  const fullPostData = {
    ...dummyPost,
    id: projectId,
    postId: dummyPost.id,
  };

  return (
    <Container>
      <Sidebar/>
      <main className={styles.mainContent}>
        <PostCard post={fullPostData} isFullPage={true} />
      </main>
    </Container>
  );
};

export default PostPage;
