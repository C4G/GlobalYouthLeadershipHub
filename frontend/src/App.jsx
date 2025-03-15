import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import SignUpPage from "@/pages/SignUpPage";
import SignUpSuccessPage from "@/pages/SignUpSuccessPage";
import TeamHomepage from "@/pages/TeamHomepage";
import Landing from "@/pages/LandingPage";
import AdminPage from "@/pages/AdminPage";
import "@/styles/App.css";
import { useSyncLocalStorage } from "@/hooks/useAuth";

function App() {
  useSyncLocalStorage()

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/team-homepage" element={<TeamHomepage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signup-success" element={<SignUpSuccessPage />} />
        <Route path="/landing" element={<Landing />} />
        <Route path="/admin" element={<AdminPage />} />
      </Routes>
    </Router>
  );
}

export default App;
