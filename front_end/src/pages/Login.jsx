import "../css/Login.css";

function Login() {
  return (
    <div className="login-page">
      <div className="login-container">
        <h2>Login Page</h2>
        <div className="login-form">
          <form>
            <div className="form-group">
              <label htmlFor="username">Username:</label>
              <input type="text" id="username" name="username" />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password:</label>
              <input type="password" id="password" name="password" />
            </div>
            <button type="submit">Login</button>
          </form>
        </div>
      </div>
    </div>
  );
}
export default Login;
