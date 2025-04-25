/* eslint-disable react/prop-types */
import { useState } from "react";
import Spinner from "@/components/Spinner";
import EmptyList from "@/components/EmptyList";
import ProjectCard from "@/components/ProjectCard";
import Pagination from "@/components/Pagination";

import styles from "./ProjectList.module.css";

const ProjectList = ({ projects, isLoading }) => {
    const ITEMS_PER_PAGE = 10
    const [currentPage, setCurrentPage] = useState(1);

    if (isLoading) return <Spinner text={"Fetching all Projects..."} />
    if (projects.length === 0) return <EmptyList text={"No Projects Found"} />;

    // Compute the slice for current page:
    const totalItems = projects.length;
    const startIdx = (currentPage - 1) * ITEMS_PER_PAGE;
    const endIdx = startIdx + ITEMS_PER_PAGE;
    const paginatedPosts = projects ? projects.slice(startIdx, endIdx) : [];

    return (
        <>
            <div className={styles.projectGridFlex}>
                {paginatedPosts.map(project => {
                    return (
                        <ProjectCard key={project.id} project={project} />
                    )
                })}
            </div>
            <Pagination
                totalItems={totalItems}
                itemsPerPage={ITEMS_PER_PAGE}
                currentPage={currentPage}
                onPageChange={(page) => setCurrentPage(page)}
            />
        </>
    )
}

export default ProjectList