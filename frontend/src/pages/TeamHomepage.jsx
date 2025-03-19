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

      <section className={styles.section} aria-labelledby="survey">
        <h2 id="survey">Peer Evaluation Survey</h2>
        <p className={styles.paragraph}>
          Please participate in our Project Peer Evaluations by completing the
          following survey:{" "}
          <a
            href="https://forms.office.com/r/fRkVYx6HNF"
            target="_blank"
            rel="noopener noreferrer"
          >
            Peer Evaluation Survey
          </a>
        </p>
      </section>

      <section className={styles.section} aria-labelledby="tasks">
        <h2 id="tasks">Evaluation Tasks</h2>
        <p className={styles.paragraph}>
          Hi! Our team would like to request your participation in the peer
          evaluation of our app.
        </p>
        <p className={styles.paragraph}>
          We have 3 tasks that we would like you to complete. Please perform the
          tasks, then record the time taken to perform each task and answer
          the follow-up questions to provide us with feedback.
        </p>
        <p className={styles.paragraph}>
          The web application can be accessed at&nbsp;
          <a
            href="https://legacyintl.c4g.dev/"
            target="_blank"
            rel="noopener noreferrer"
          >
             https://legacyintl.c4g.dev/
          </a>
        </p>

        <h3>Task 1: Register a new account and return to the login page</h3>
        <p className={styles.paragraph}>
          - Try registering for a new account, then return to the login page by
          clicking on the logo.
        </p>
        <p className={styles.paragraph}>
          - <em>Expected result:</em> You should observe a Successful Registration screen and navigate to the login page.
        </p>

        <h3>Task 2: Log in with the account you created and perform tasks</h3>
        <p className={styles.paragraph}>
          - You should be able to log in, with limited access to pages and features.
        </p>
        <p className={styles.paragraph}>
          - View the landing page, try creating a new post, reply to comments on each post, and click Like before logging out.
        </p>
        <p className={styles.paragraph}>
          - <em>Expected result:</em> You should be able to log in with the credentials used in the previous task and be redirected to the landing page. On the landing page, you should be able to perform the tasks mentioned above. You will be redirected back to the log in page after logging out.
        </p>

        <h3>Task 3: Log in as an admin and verify a user</h3>
        <p className={styles.paragraph}>
          - Now try logging in as an admin and navigate to the admin-only page from the side bar.
        </p>
        <p className={styles.paragraph}>
          - Verify the user that you just created.
        </p>
        <p className={styles.paragraph}>
          - The credentials are as follows:
          <br />
          Email: admin@gmail.com
          <br />
          Password: admin123
        </p>
        <p className={styles.paragraph}>
          - <em>Expected result:</em> You should be able to log in as an admin, navigate to the admin page and verify a user.
        </p>

        <h3>The same questions are reused for all 3 tasks</h3>
        <p className={styles.paragraph}>
          1.How easy did you find the task? (1-5 rating, with 1 being very easy and 5 being very difficult)
        </p>
        <p className={styles.paragraph}>
          2.How long did the task take? (please record the time taken to complete the task and select an option below)
        </p>
        <p className={styles.paragraph}>
          3.General feedback
        </p>
      </section>

      <section className={styles.section} aria-labelledby="team-members">
        <h2 id="team-members">Team Members & Contributions</h2>
        <ul>
          <li>
            <b>Kevin E Taylor:</b>
            <ul>
              <li>Built the backend API</li>
              <li>Implemented Role-Based Access Controls</li>
              <li>Automated database migration scripts</li>
            </ul>
          </li>
          <li>
            <b>Jun Siang Neo:</b>
            <ul>
              <li>Co-designed UI/UX components</li>
              <li>Set up cloud and Dockerfiles for deployment</li>
              <li>Built and integrated user registration and login flow</li>
            </ul>
          </li>
          <li>
            <b>Yong-Chang Chen:</b>
            <ul>
              <li>
                Developed the front-end web pages, including&nbsp;
                <a
                  href="https://legacyintl.c4g.dev/"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Login
                </a>
                ,&nbsp;
                <a
                  href="https://legacyintl.c4g.dev/signup"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Register Account
                </a>
                , and&nbsp;
                <a
                  href="https://legacyintl.c4g.dev/landing"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Landing Page
                </a>
                .
              </li>
              <li>
                Adapted for both web and mobile views, incorporating
                accessibility, SEO, and performance considerations.
              </li>
              <li>
                Created the&nbsp;
                <a
                  href="https://legacyintl.c4g.dev/team-homepage"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Team Project Home Page
                </a>
                .
              </li>
            </ul>
          </li>
          <li>
            <b>Jeffrey Tan Jian Sheng:</b>
            <ul>
              <li>Co-designed UI/UX components</li>
              <li>Drafted and created surveys for evaluations</li>
              <li>Performed integration and deployment testing</li>
              <li>Worked on backend API development</li>
            </ul>
          </li>
          <li>
            <b>Franklyn Abraham Diaz:</b>
            <ul>
              <li>Assisted with PR actions</li>
              <li>Developed backend API for project access</li>
            </ul>
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
        <p className={styles.footerLink}>
          <a
            aria-label="project home page"
            href="https://legacyintl.c4g.dev/"
            className={styles.projectHomepageLink}
          >
            Project Homepage
          </a>
        </p>
      </footer>
    </div>
  );
};

export default TeamHomepage;
