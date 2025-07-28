import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem("user"));

  useEffect(() => {
    if (!user) {
      navigate("/"); // Redirect to login page
    }
  }, [user, navigate]);

  if (!user) return null; // Avoid rendering before redirect

  return (
    <div style={{ padding: "20px" }}>
      <h1>
        Welcome, {user.firstName} {user.lastName}
      </h1>
      <p>Role: {user.role}</p>
      <button
        onClick={() => {
          sessionStorage.removeItem("user");
          navigate("/");
        }}
      >
        Logout
      </button>
    </div>
  );
};

export default Home;
