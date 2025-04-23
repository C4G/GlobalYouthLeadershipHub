import { useState } from "react";
import styles from "@/styles/pages/ProjectsPage.module.css";
import Sidebar from "@/components/Sidebar";
import CreateProject from "@/components/CreateProject";
import { useGetProjects } from "@/hooks/projects";
import ProjectList from "@/components/ProjectList";
import Pagination from "@/components/Pagination";
import Container from "@/components/Container";

const ProjectsPage = () => {
  const [isModalOpen, setModalOpen] = useState(false);
  const { data: projects = [], isLoading, refetch: refetchAllProjects } = useGetProjects()

  // **Pagination state**
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;
  const totalItems = projects.length;
   
  // Compute the slice for current page:
  const startIdx = (currentPage - 1) * itemsPerPage;
  const endIdx = startIdx + itemsPerPage;
  const paginatedPosts = projects.slice(startIdx, endIdx);

  const handleCloseModal = () => {
    setModalOpen(false)
    refetchAllProjects()
  }

  return (
    <Container>
      <Sidebar isLandingPage={true} setModalOpen={setModalOpen} />
      <main className={styles.mainContent}>
        <h1 className={styles.header}>Project Dashboard</h1>
        {isModalOpen && (
          <CreateProject
            onClose={handleCloseModal}
          />
        )}
        <ProjectList projects={paginatedPosts} isLoading={isLoading} />

        <Pagination
          totalItems={totalItems}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          onPageChange={(page) => setCurrentPage(page)}
        />

      </main>
    </Container >
  );
};

export default ProjectsPage;
