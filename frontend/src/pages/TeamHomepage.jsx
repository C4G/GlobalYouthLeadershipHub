import styles from "@/styles/pages/TeamHomepage.module.css";

const TeamHomepage = () => {
  return (
    <div className={styles.container}>
      <header className={styles.header} aria-label="Project Title">
        <h1>Global Youth Leadership Sustainability Hub</h1>
        <p className={styles.paragraph}>
          A web platform designed for Legacy International’s alumni network,
          supporting leadership and sustainable development through global youth
          programs.
        </p>
      </header>

      <section className={styles.section} aria-labelledby="project-description">
        <h2 id="project-description">Project Description</h2>
        <p className={styles.paragraph}>
          The project aims to create a secure and user-friendly portal where
          alumni from U.S. State Department-funded leadership exchange programs
          can connect, collaborate, and share impactful initiatives.
        </p>
        <ul>
          <li>
            A secure login system with optional contact-sharing capabilities.
          </li>
          <li>A repository of alumni projects for knowledge exchange.</li>
          <li>Responsive design for mobile and desktop users.</li>
          <li>
            Optimized for varying internet speeds to ensure accessibility.
          </li>
          <li>
            Focus on frontend development for intuitive UX with strong backend
            security.
          </li>
        </ul>
      </section>

      <section className={styles.section} aria-labelledby="team-members">
        <h2 id="team-members">Team Members & Roles</h2>
        <ul>
          <li aria-label="Kevin E Taylor, Back-end">
            Kevin E Taylor - Back-end
          </li>
          <li aria-label="Jeffrey Tan Jian Sheng, UI UX and Back-end">
            Jeffrey Tan Jian Sheng - UI/UX + Back-end
          </li>
          <li aria-label="Yong-Chang Chen, Front-end">
            Yong-Chang Chen - Front-end
          </li>
          <li aria-label="Jun Siang Neo, UI UX and Cloud">
            Jun Siang Neo - UI/UX + Cloud
          </li>
          <li aria-label="Franklyn Abraham Diaz, Cloud">
            Franklyn Abraham Diaz - Cloud
          </li>
        </ul>
      </section>

      <section className={styles.section} aria-labelledby="project-goal">
        <h2 id="project-goal">Project Goal</h2>
        <h3>Must-Haves:</h3>
        <ul>
          <li>
            Networking and Collaboration Tools: Chatboards, forums, and
            collaborative workspaces.
          </li>
        </ul>
        <h3>Good-to-Haves:</h3>
        <ul>
          <li>
            Project Showcases: Highlight alumni-led projects on a global map.
          </li>
          <li>
            Professional Development: Webinars, mentorship, and job boards.
          </li>
          <li>
            Resource Library: Toolkits, funding opportunities, training
            materials.
          </li>
        </ul>
        <h3>Stretch Goals:</h3>
        <ul>
          <li>
            Gamification Elements: Badges and leaderboards for engagement.
          </li>
          <li>
            Impact Tracking: Tools to log milestones and measure outcomes.
          </li>
        </ul>
      </section>

      <section className={styles.section} aria-labelledby="lighthouse-scores">
        <h2 id="lighthouse-scores">Lighthouse Scores</h2>
        <p className={styles.paragraph}>
          Below are the Lighthouse scores for performance, accessibility, best
          practices, and SEO.
        </p>
        <ul>
          <li>Performance: 100</li>
          <li>Accessibility: 100</li>
          <li>Best Practices: 100</li>
          <li>SEO: 91</li>
        </ul>
      </section>

      <footer className={styles.footer} aria-label="Footer">
        <p className={styles.paragraph}>
          <a aria-label="project home page" href="/" className={styles.projectHomepageLink}>Project Homepage</a>
        </p>
      </footer>
    </div>
  );
};

export default TeamHomepage;
