import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import SignUpSuccessPage from "@/pages/SignUpSuccessPage";
import TeamHomepage from "@/pages/TeamHomepage";
import Landing from "@/pages/LandingPage";
import AdminPage from "@/pages/AdminPage";
import "@/styles/App.css";
import { useSyncLocalStorage } from "@/hooks/auth";
import { ProtectedAdminRoute, ProtectedRoutes } from "./components/ProtectedRoutes";

function App() {
  useSyncLocalStorage()

  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signup-success" element={<SignUpSuccessPage />} />
        <Route path="/team-homepage" element={<TeamHomepage />} />
        {/* Login Routes */}
        <Route path="/landing" element={
          <ProtectedRoutes>
            <Landing />
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
