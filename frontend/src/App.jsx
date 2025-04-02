import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import SignUpSuccessPage from "@/pages/SignUpSuccessPage";
import TeamHomepageSpr25 from "@/pages/team/TeamHomepageSpr25";
import Landing from "@/pages/LandingPage";
import AdminPage from "@/pages/AdminPage";
import ProjectPage from "@/pages/ProjectPage";
import PostsPage from "@/pages/PostsPage";
import { ProtectedAdminRoute, ProtectedRoutes } from "@/components/ProtectedRoutes";
import { useJWTToken, useSyncLocalStorage } from "@/hooks/auth";

import "@/styles/App.css";

function App() {
  useSyncLocalStorage()

  const { data: jwtToken } = useJWTToken()
  const isLoggedIn = Boolean(jwtToken?.token)

  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={isLoggedIn ? <Navigate to="/landing" replace /> : <LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signup-success" element={<SignUpSuccessPage />} />
        {/* Area for future teams working on this project to display their team homepage */}
        {/* Please keep this route. It's fine if the link isn't displayed on any page, but kindly don't remove it permanently. Thanks!*/}
        <Route path="/team-homepage-spr25" element={<TeamHomepageSpr25 />} />


        {/* Login Routes */}
        <Route path="/landing" element={
          <ProtectedRoutes>
            <Landing />
          </ProtectedRoutes>}
        />

        <Route path="/project" element={
          <ProtectedRoutes>
            <ProjectPage />
          </ProtectedRoutes>}
        />

        <Route path="/projects/:projectId/posts" element={
          <ProtectedRoutes>
            <PostsPage />
          </ProtectedRoutes>}
        />

        {/* Admin Route */}
        <Route path="/admin" element={
          <ProtectedAdminRoute>
            <AdminPage />
          </ProtectedAdminRoute>
        } />
      </Routes>
    </Router>
  );
}

export default App;
