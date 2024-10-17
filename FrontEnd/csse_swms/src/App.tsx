import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom';
import Login from './components/Login';
import UserDashboard from './components/UserDashboard';
import CollectorDashboard from './components/CollectorDashboard';
import AuthorityDashboard from './components/AuthorityDashboard';

function App() {
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : null;
  });

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    } else {
      localStorage.removeItem('user');
    }
  }, [user]);

  useEffect(() => {
    // Test fetch data from the Spring Boot backend
    fetch("http://localhost:8081/api/test") 
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.text(); 
      })
      .then((data) => {
        console.log(data); 
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", error);
      });
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Routes>
          <Route 
            path="/login" 
            element={user ? <Navigate to={`/${user.role}`} replace /> : <Login onLogin={handleLogin} />} 
          />
          <Route
            path="/user/*"
            element={
              user && user.role === 'user' ? (
                <UserDashboard user={user} onLogout={handleLogout} />
              ) : (
                <Navigate to="/login" replace />
              )
            }
          />
          <Route
            path="/collector/*"
            element={
              user && user.role === 'collector' ? (
                <CollectorDashboard user={user} onLogout={handleLogout} />
              ) : (
                <Navigate to="/login" replace />
              )
            }
          />
          <Route
            path="/authority/*"
            element={
              user && user.role === 'authority' ? (
                <AuthorityDashboard user={user} onLogout={handleLogout} />
              ) : (
                <Navigate to="/login" replace />
              )
            }
          />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;