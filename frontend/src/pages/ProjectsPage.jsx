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


  // TODO - to move to createPost
  // const location = useLocation();
  // const userEmail = location.state?.user;

  // const [posts, setPosts] = useState([
  //   {
  //     initials: "AC",
  //     projectName: "Empowering Team Innovation",
  //     content:
  //       "CollabSync is a cutting-edge initiative designed to enhance team collaboration and productivity in modern, fast-paced workplaces. The project aims to create an integrated workspace where professionals can seamlessly exchange ideas, collaborate on projects, and drive innovation.  In today's dynamic business environment, effective collaboration is key to achieving project success. CollabSync addresses this by providing a platform that merges technology with human creativity. The system offers real-time communication tools, project management features, and resource-sharing capabilities that empower teams to work together efficiently—whether in the office or remotely.  The heart of CollabSync lies in its intuitive interface that encourages open dialogue, brainstorming, and problem-solving. It facilitates efficient information flow through digital whiteboards, instant messaging, and file-sharing systems, ensuring that ideas are captured and developed collaboratively. Additionally, integrated task tracking and deadline management tools help teams stay organized and on target.  A core feature of CollabSync is its focus on inclusivity and diversity of thought. By encouraging diverse team members to contribute their unique perspectives, the platform fosters a culture of innovation and creativity. Regular team huddles, project updates, and feedback loops ensure that every voice is heard, strengthening team dynamics and trust.  Ultimately, CollabSync is more than just a tool—it’s a framework for building stronger, more cohesive teams. It equips organizations with the means to navigate complex projects, adapt to changing requirements, and achieve collective success. With CollabSync, collaboration becomes effortless, focused, and impactful.",
  //     imageSrc: "/testPicture1.jpg",
  //     likes: 10,
  //     user: { name: userEmail },
  //   },
  //   {
  //     initials: "MJ",
  //     projectName: "NextGen Soccer Stars",
  //     content:
  //       "NextGen Soccer Stars is an inspiring youth program dedicated to nurturing the next generation of soccer players through fun, engaging, and skill-focused activities. The initiative is designed to help children develop their athletic abilities while promoting essential life values such as teamwork, discipline, and perseverance.  The core mission of NextGen Soccer Stars is to provide a supportive environment where young players can learn fundamental soccer techniques, practice strategic thinking, and gain confidence through friendly competition. Each session is structured to balance skill-building with enjoyment, ensuring that every participant feels encouraged and motivated to grow both as an athlete and as an individual.  Inclusivity is a key pillar of the program. NextGen Soccer Stars works closely with local communities to make soccer accessible to all children by providing equipment, coaching resources, and a safe space to play. Regardless of background or experience level, every child is given the opportunity to participate, improve, and feel a sense of belonging.  Beyond the field, the program emphasizes personal development by introducing educational workshops on topics like healthy nutrition, leadership, and goal-setting. These sessions help children develop habits that contribute to long-term well-being and success.  NextGen Soccer Stars is more than a sports initiative—it’s a platform for building confidence, fostering friendships, and empowering young players to embrace challenges with courage and enthusiasm. Through soccer, children learn skills that shape them into stronger individuals, ready to take on any goal in life.",
  //     imageSrc: "/testPicture2.jpg",
  //     likes: 9,
  //     user: { name: userEmail },
  //   },
  // ]);
};

export default ProjectsPage;
