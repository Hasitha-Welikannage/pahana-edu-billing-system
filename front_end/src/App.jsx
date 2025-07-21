import { Routes, Route } from "react-router-dom";
import "./App.css";

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
