import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUserAPI } from "../services/authService"; // Adjust the import path as necessary

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    const success = await loginUserAPI(username, password);
    if (success) {
      //navigate("/dashboard");
    } else {
      setError("Login failed");
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <form onSubmit={handleLogin}>
          <h2>Login</h2>
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
          />
          <button type="submit">Login</button>
          {error && <p className="error">{error}</p>}
        </form>
      </div>
    </div>
  );
}

export default Login;
