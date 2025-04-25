import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import ForgotPasswordPage from "@/pages/ForgotPasswordPage";
import SignUpPage from "@/pages/SignUpPage";
import SignUpSuccessPage from "@/pages/SignUpSuccessPage";
import TeamHomepageSpr25 from "@/pages/team/TeamHomepageSpr25";
import ProjectsPage from "@/pages/ProjectsPage";
import AdminPortalPage from "@/pages/AdminPortalPage";
import MyProjectPage from "@/pages/MyProjectPage";
import PostsPage from "@/pages/PostsPage";
import PostPage from "@/pages/PostPage";
import UnauthorizedPage from "@/pages/UnauthorizedPage";
import { ProtectedAdminRoute, ProtectedRoutes } from "@/components/ProtectedRoutes";
import { useJWTToken, useSyncLocalStorage } from "@/hooks/auth";

import "./App.css";

function App() {
  useSyncLocalStorage()

  const { data: jwtToken } = useJWTToken()
  const isLoggedIn = Boolean(jwtToken?.token)

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/" element={isLoggedIn ? <Navigate to="/landing" replace /> : <LoginPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/signup-success" element={<SignUpSuccessPage />} />
      <Route path="/forgot-password" element={<ForgotPasswordPage />} />
      {/* isLoggedIn is needed as to prevent from unverified user from re-accessing the unauthorized page with back button */}
      <Route path="/unauthorized" element={isLoggedIn ? <UnauthorizedPage /> : <Navigate to="/" replace />} />

      {/* Login Routes */}
      <Route path="/landing" element={
        <ProtectedRoutes>
          <ProjectsPage />
        </ProtectedRoutes>}
      />

      <Route path="/project" element={
        <ProtectedRoutes>
          <MyProjectPage />
        </ProtectedRoutes>}
      />

      <Route path="/projects/:projectId/posts" element={
        <ProtectedRoutes>
          <PostsPage />
        </ProtectedRoutes>}
      />

      <Route path="/projects/:projectId/posts/:postId" element={
        <ProtectedRoutes>
          <PostPage />
        </ProtectedRoutes>}
      />

      {/* Admin Route */}
      <Route path="/admin" element={
        <ProtectedAdminRoute>
          <AdminPortalPage />
        </ProtectedAdminRoute>
      } />

      <>
        {/* Area for future teams working on this project to display their team homepage */}
        {/* Please keep this route. It's fine if the link isn't displayed on any page, but kindly don't remove it permanently. Thanks!*/}
        <Route path="/team-homepage-spr25" element={<TeamHomepageSpr25 />} />
      </>
    </Routes>
  );
}

export default App;
