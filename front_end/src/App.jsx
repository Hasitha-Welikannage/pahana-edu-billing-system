import { Routes, Route } from "react-router-dom";
import "./css/App.css";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard"; // Assuming you have a Dashboard component

function App() {
  return (
    <main className="main-container">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </main>
  );
}

export default App;
