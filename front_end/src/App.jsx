import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/login/Login";
import Home from "./pages/dashboard/Home";

function App() {
  return (
    <main className="main-container">
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
      </Routes>
    </main>
  );
}

export default App;
