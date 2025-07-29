import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Home.css';

const Home = () => {
  const navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem('user'));

  useEffect(() => {
    if (!user) {
      navigate('/');
    }
  }, [user, navigate]);

  if (!user) return null;

  return (
    <div className="home-container">
      <div className="header">
        <h2>Welcome, {user.firstName} {user.lastName}</h2>
        <p>Role: <strong>{user.role}</strong></p>
        <button className="logout-btn" onClick={() => {
          sessionStorage.removeItem('user');
          navigate('/');
        }}>Logout</button>
      </div>

      <div className="card-grid">
        {user.role === 'ADMIN' && (
          <>
            <div className="card" onClick={() => navigate('/users')}>
              <h3>User Management</h3>
              <p>Manage system users and their roles</p>
            </div>
            <div className="card" onClick={() => navigate('/items')}>
              <h3>Item Management</h3>
              <p>Add or update bookshop items</p>
            </div>
          </>
        )}

        <div className="card" onClick={() => navigate('/customers')}>
          <h3>Customer Management</h3>
          <p>Manage customer records and details</p>
        </div>

        <div className="card" onClick={() => navigate('/create-bill')}>
          <h3>Bill Creation</h3>
          <p>Create and print a new bill</p>
        </div>

        <div className="card" onClick={() => navigate('/bill-history')}>
          <h3>Bill History</h3>
          <p>View all created bills and details</p>
        </div>
      </div>
    </div>
  );
};

export default Home;
