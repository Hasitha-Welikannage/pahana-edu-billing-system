import { Routes, Route } from "react-router-dom";
import "./css/App.css";
import Login from "./pages/Login";

function App() {
  return (
    <main className="main-container">
      <Routes>
        <Route path="/" element={<Login />} />
      </Routes>
    </main>
  );
}

export default App;
