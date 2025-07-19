import "../css/Login.css";
import {useState} from "react";
import { useNavigate } from 'react-router-dom';

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Function to handle form submission
  const handleSubmit = (event) => {
    event.preventDefault();
    if (username === "admin" && password === "password") {
      navigate("/dashboard"); // Navigate to the dashboard on successful login
      setError(""); // Clear any previous error messages
    } else {
      setError("Invalid username or password");
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h2>Login Page</h2>
        <div className="login-form">
          <form>
            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input type="text" id="username" name="username" onChange={(e) => setUsername(e.target.value)} />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input type="password" id="password" name="password" onChange={(e) => setPassword(e.target.value)} />
            </div>
            <button type="submit" onClick={handleSubmit}>Login</button>
            {error && <p style={{color:'red'}}>{error}</p>}
          </form>
        </div>
      </div>
    </div>
  );
}
export default Login;
