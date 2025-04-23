import Spinner from "@/components/Spinner";
import ProjectCard from "@/components/ProjectCard";
import EmptyList from "@/components/EmptyList";
import { useGetProjectsByUser } from "@/hooks/projects"
import styles from "@/styles/components/MyProjects.module.css"

const MyProjects = () => {
    const { data: myProjects, isLoading } = useGetProjectsByUser()

    if (isLoading) {
        return (
            <div className={styles.myProjectsSpinner}>
                <Spinner text={"Fetching all projects..."} />
            </div>
        )
    }

    return (
        <>
            <div className={styles.myProjectsList}>
                {myProjects.length === 0 && <EmptyList text={"No User Related Projects Found"} />}
                {myProjects.map(project => <ProjectCard key={project.id} project={project} />)}
            </div>
        </>
    )
}

export default MyProjects