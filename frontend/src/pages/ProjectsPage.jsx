import { useState } from "react";
import styles from "@/styles/pages/ProjectsPage.module.css";
import Sidebar from "@/components/Sidebar";
import CreateProject from "@/components/CreateProject";
import { useGetProjects } from "@/hooks/projects";
import ProjectList from "@/components/ProjectList";
import Container from "@/components/Container";

const ProjectsPage = () => {
  const [isModalOpen, setModalOpen] = useState(false);
  const { data: projects, isLoading, refetch: refetchAllProjects } = useGetProjects()

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
        <ProjectList projects={projects} isLoading={isLoading} />
      </main>
    </Container >
  );
};

export default ProjectsPage;
