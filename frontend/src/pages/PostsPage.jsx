import { useParams } from "react-router-dom";
import PostCard from "@/components/PostCard";
import Sidebar from "@/components/Sidebar";
import styles from "@/styles/pages/ProjectPage.module.css"

const PostsPage = () => {
    const { projectId } = useParams()
    // TODO - to revise once API is up
    console.log("pId", projectId)

    return (
        <div className={styles.container}>
            <Sidebar />
            <main className={styles.mainContent}>
                {/* TODO - to enable once API is wire up */}
                <PostCard />
            </main>
        </div>
    );
}

export default PostsPage