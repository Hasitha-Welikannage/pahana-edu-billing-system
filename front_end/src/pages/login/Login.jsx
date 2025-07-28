import { useState } from 'react';
import './Login.css';

const Login = () => {
  const [form, setForm] = useState({ username: '', password: '' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      const response = await fetch('http://localhost:8080/back_end/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });

      const data = await response.json();

      if (data.success) {
        setMessage(data.message);
        sessionStorage.setItem('user', JSON.stringify(data.data)); // store user info
        window.location.href = '/home'; // navigate to home page
      } else {
        setError(`${data.errorCode} (Code: ${data.message})`);
      }
    } catch (err) {
      setError('Something went wrong. Please try again.');
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>

      {message && <div className="success-msg">{message}</div>}
      {error && <div className="error-msg">{error}</div>}
    </div>
  );
};

export default Login;
