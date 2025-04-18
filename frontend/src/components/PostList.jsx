/* eslint-disable react/prop-types */
import Spinner from "@/components/Spinner";
import EmptyList from "@/components/EmptyList";
import PostCard from "@/components/PostCard";

const PostList = ({ posts, isLoading, projectName = "" }) => {
    if (isLoading) {
        return (
            <>
                <Spinner text={"Fetching all Posts..."} />
            </>
        )
    }

    return (
        <>
            {posts.length === 0 && <EmptyList text={"No Posts Found"} />}
            {posts.map(post => {
                return (
                    <PostCard key={post.id} post={post} projectName={projectName}/>
                )
            })}
        </>
    )
}

export default PostList