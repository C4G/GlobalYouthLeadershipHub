import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./styles/App.css";
import LoginPage from "./components/LoginPage";
import LoginSuccessPage from "./components/LoginSuccessPage";
import SignUpPage from "./components/SignUpPage";
import SignUpSuccessPage from "./components/SignUpSuccessPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/login-success" element={<LoginSuccessPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signup-success" element={<SignUpSuccessPage />} />
      </Routes>
    </Router>
  );
}

export default App;
