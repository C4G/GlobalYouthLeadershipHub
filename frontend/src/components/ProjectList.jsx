/* eslint-disable react/prop-types */
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
        <>
            {projects.length === 0 && <EmptyList text={"No Projects Found"} />}
            {projects.
                // TODO - temporary as this should be sort by createdAt
                sort((a, b) => b.id - a.id).
                map(project => {
                    return (
                        <ProjectCard key={project.id} project={project} />
                    )
                })}
        </>
    )
}

export default ProjectList