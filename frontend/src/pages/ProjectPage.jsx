import Sidebar from "@/components/Sidebar";
// import Spinner from "@/components/Spinner";
// import { useGetProjectByUserId } from "@/hooks/projects";
import styles from "@/styles/pages/ProjectPage.module.css"

const ProjectPage = () => {

    {/* TODO - to enable once API is wire up */ }
    // const { data, isLoading } = useGetProjectByUserId()

    {/* TODO - to enable once API is wire up */ }
    // if (isLoading) {
    //     return <Spinner text={"Fetching User Project..."} />
    // }

    return (
        <div className={styles.container}>
            <Sidebar />
            <main className={styles.mainContent}>
                {/* TODO - to enable once API is wire up */}
                <div className={styles.underConstruction}>
                    <h1>🚧 Page Under Construction 🚧</h1>
                    <p>We&apos;re working hard to bring this page to life. Stay tuned!</p>
                </div>
            </main>
        </div>
    );
}

export default ProjectPage