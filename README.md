# Global Youth Leadership Sustainability Hub

A centralized, user-friendly platform for alumni of the On-Demand Youth Leadership Program (ODYLP) administered by Legacy International to connect, collaborate, and amplify their global impact.

---

## Table of Contents

- [Project Description](#project-description)
- [Architecture Diagram & Description](#architecture-diagram--description)
- [Data Flow for Key User Stories](#data-flow-for-key-user-stories)
- [Hosting](#hosting)
- [Application Installation](#application-installation)
- [Authentication & Authorization](#authentication--authorization)
- [Database Backup](#database-backup)
- [Partner Statement](#partner-statement)
- [Guided Walkthrough](#guided-walkthrough)
- [Deliverable Screenshots & Known Liabilities](#deliverable-screenshots--known-liabilities)
- [UX Considerations](#ux-considerations)

---

## Project Description

The Global Youth Leadership Sustainability Hub addresses the need for a dynamic, inclusive, and centralized digital platform for alumni to:

- Share successes and lessons learned
- Collaborate on cross-border projects
- Amplify the global impact of their work

**Key Features:**

- Alumni project showcase
- Networking and collaboration tools: chat boards similar to Reddit
- Email-based login with admin approval
- Accessibility for varying internet speeds and English proficiency levels
- [Stretch] Milestone tracking and professional development (webinars, mentorship, job boards)
- [Stretch] Global map by region and theme

---

## Architecture Diagram & Description

**Architecture Diagram:**
![Architecture Diagram Placeholder](/assets/Legacy_International_Architecture.png)

**Architecture Overview:**

- **Frontend:** JavaScript, React, CSS3
- **Backend:** Java Spring Boot REST API
- **Database:** MySQL TODO: To confirm database on deployment
- **Hosting:** Open-source, managed by C4G of Georgia Institute of Technology at no cost as of Fall 2025

The frontend communicates with the backend via RESTful API calls. The backend handles authentication, authorization, data storage, and business logic. All sensitive operations (e.g., user verification, project creation) require proper authentication.

---

## Data Flow for Key User Stories

| User Story                         | Frontend File(s)                      | Backend Endpoint(s)                               | Database Table(s)                                                                   |
| ---------------------------------- | ------------------------------------- | ------------------------------------------------- | ----------------------------------------------------------------------------------- |
| Register as a user                 | `LoginPage.jsx`                       | `/api/auth/register`                              | `sustSch.users`                                                                     |
| Admin login                        | `LoginPage.jsx`                       | `/api/auth/login`                                 | `sustSch.users`                                                                     |
| Verify user as admin               | `AdminPortalPage.jsx`                 | `/api/admin/users/verify`                         | `sustSch.users`                                                                     |
| User login (after verification)    | `LoginPage.jsx`                       | `/api/auth/login`                                 | `sustSch.users`                                                                     |
| Create a project from landing page | `ProjectsPage.jsx, CreateProject.jsx` | `/api/projects`                                   | `sustSch.projects`                                                                  |
| Create a post from project page    | `CreatePost.jsx`                      | `/api/projects/:projectId/posts`                  | `sustSch.posts`, `sustSch.projects`, `sustSch.post_images`                          |
| Create a comment from post page    | `Comment.jsx`                         | `/api/projects/:projectId/posts/:postId/comments` | `sustSch.posts`, `sustSch.projects`, `sustSch.post_images`, `sustSch.post_comments` |

---

## Hosting

- **Database:** TODO: To confirm database on deployment
- **Website:** Managed by C4G of Georgia Institute of Technology at no cost as of Fall 2025 (TODO: Update with more details if available)

---

## Application Installation

**Prerequisites:**

- Node.js (for frontend)
- Java 17+ (for backend)
- Git
- \*Recommended IntelliJ Idea provided by GaTech
- API Development Client (i.e. Postman or Insomnia)

**Steps:**

1. **Clone the repository:**

   ```sh
   git clone https://github.gatech.edu/cs-6150-computing-for-good/Global-Youth-Leadership-Sustainability-Hub.git
   cd Global-Youth-Leadership-Sustainability-Hub
   ```

2. **Frontend Setup:**

   ```sh
   cd frontend
   npm install
   npm start
   ```

3. **Run docker to set up backend and database:**

   - TODO: Add steps to set up

4. **Accessing the app:**

   - Frontend: `http://localhost:3000`
   - Backend: `http://localhost:8080`

---

## Authentication & Authorization

- **User Authentication:** Email/password, managed via Spring Security in the backend.
- **Authorization:** New users require admin approval (via admin portal).
- Admin credentials: `admin@gmail.com` / `admin123` (default, change in production).
- **Files Involved:**
  - Backend: `AuthController.java`, `UserService.java`, `SecurityConfig.java`
  - Frontend: `LoginPage.jsx`, `AdminPortalPage.jsx`
- **Technology:** JWT tokens for session management.
- **Security:** Ensure `.env` is in `.gitignore` and should never be pushed to the repo.

---

## Database Backup

TODO: We need a backup file and more details on this

- **Backup File:** `backup.sql` (provided in the `/db-backups` directory)
- **Restore:** mysql

---

## Partner Statement

TODO: Review and modify according to whether partner adopt our app

> Partner understands the basics of the developer documentation and is aware of the open-source deployment and transition plan.

---

## Guided Walkthrough

- **Date:** April 18, 2025
- **Walkthrough with:** Chelsea (Partner from Legacy International) and Mitchell (C4G Mentor)
- **Summary:** Guided through repository setup, environment configuration, and deployment details as per this documentation.

---

## Deliverable Screenshots & Known Liabilities

### Lighthouse Metrics

TODO: Replace with the actual screenshots
![Lighthouse Metrics](lighthouse-metrics.jpg)

### Form Factor Analysis

TODO: Add the detailed form factor analysis done by Aaron

| Device             | Screenshot Placeholder                            |
| ------------------ | ------------------------------------------------- |
| Mobile - Portrait  | ![Mobile Portrait](/assets/mobile-portrait.jpg)   |
| Mobile - Landscape | ![Mobile Landscape](/assets/mobile-landscape.jpg) |
| Desktop            | ![Desktop](/assets/desktop.jpg)                   |

**Known Liabilities:**

- Some UI elements may not be rendered perfectly on older mobile browsers; consider using responsive CSS frameworks.
- Admin workflows (user verification and password reset) may need optimization for large user volumes.
- Email delivery not supported yet; notification of user verification has to be done manually.
- Accessibility for non-English speakers is basic; future improvements could include localization/internationalization support.

---

## UX Considerations

- **User-friendly navigation** with clear calls-to-action for project creation and collaboration.
- **Inclusive design:** Simple language, large clickable areas, and mobile responsiveness.
- **Minimal hardware requirements** to ensure accessibility across regions with varying internet speeds.
- **Manual admin approval** ensures community integrity and security.

---

_For further questions or support, please refer to the Team Homepage or contact the Instructional Team of C4G._
