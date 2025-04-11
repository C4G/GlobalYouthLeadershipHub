/* eslint-disable react/prop-types */
import styles from "@/styles/components/ProjectList.module.css";
import Spinner from "@/components/Spinner";
import EmptyList from "@/components/EmptyList";
import ProjectCard from "@/components/ProjectCard";

const ProjectList = ({ projects, isLoading }) => {
    if (isLoading) {
        return (
            <>
                <Spinner text={"Fetching all Projects..."} />
            </>
        )
    }

    return (
        <div className={styles.projectGridFlex}>
            {projects.length === 0 && <EmptyList text={"No Projects Found"} />}
            {projects.map(project => {
                return (
                    <ProjectCard key={project.id} project={project} />
                )
            })}
        </div>
    )
}

export default ProjectList