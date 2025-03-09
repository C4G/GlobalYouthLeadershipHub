import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "@/styles/App.css";
import TeamHomepage from "./components/TeamHomepage";
import LoginPage from "@/pages/LoginPage";
import LoginSuccessPage from "@/pages/LoginSuccessPage";
import SignUpPage from "@/pages/SignUpPage";
import SignUpSuccessPage from "@/pages/SignUpSuccessPage";
import Landing from "./components/LandingPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/team-homepage" element={<TeamHomepage />} />
        <Route path="/" element={<LoginPage />} />
        <Route path="/login-success" element={<LoginSuccessPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signup-success" element={<SignUpSuccessPage />} />
        <Route path="/landing" element={<Landing />} />
      </Routes>
    </Router>
  );
}

export default App;
