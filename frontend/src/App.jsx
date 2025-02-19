import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./styles/App.css";
import LandingPage from "./components/LandingPage";
import SignUpPage from "./components/SignUpPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/signup" element={<SignUpPage />} />
      </Routes>
    </Router>
  )
}

export default App
