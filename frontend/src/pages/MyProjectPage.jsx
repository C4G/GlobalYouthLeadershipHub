import Container from "@/components/Container";
import Sidebar from "@/components/Sidebar";
import Spinner from "@/components/Spinner";
import { useGetProjectByUserId } from "@/hooks/projects";
import ProjectList from "@/components/ProjectList";
import styles from "@/styles/pages/MyProjectPage.module.css"

const MyProjectPage = () => {

    {/* TODO - to enable once API is wire up */ }
    // const { data, isLoading } = useGetProjectByUserId()

    {/* TODO - to enable once API is wire up */ }
    // if (isLoading) {
    //     return <Spinner text={"Fetching User Project..."} />
    // }

    const { data: myProjects, isLoading } = useGetProjectByUserId();

    return (
        <Container>
            <Sidebar />
            <main className={styles.mainContent}>
                {/* TODO - to enable once API is wire up */}
                {isLoading ? (
                    <Spinner text={"Fetching Your Projects..."} />
                ) : (
                    <ProjectList projects={myProjects} isLoading={isLoading} />
                )}
            </main>
        </Container>
    );
}

export default MyProjectPage