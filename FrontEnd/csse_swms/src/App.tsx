import React, { useEffect, useState } from 'react';
import axios from 'axios';
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
import BusinessSignup from './components/signup/BusinessReg';
import HouseholdSignup from './components/signup/HouseholdReg';
import PaymentForm from './components/user/Payment';

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
      // sessionStorage.removeItem('token');
    }
  }, [user]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem('token');
        const response = await axios.get("http://localhost:8081/api/test", {
          headers: {
        Authorization: `Bearer ${token}`
          }
        });
        console.log("Data from the server:", response.data);
      } catch (error) {
        console.error("There was a problem with the fetch operation:", error);
      }
    };

    fetchData();
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
  };

  const isBusinessOrHouseholder = (userRole) => {
    return userRole === 'business' || userRole === 'householder';
  };

  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Routes>
          <Route
            path="/signup"
            element={            
                <BusinessSignup />         
            }
          />
          <Route
            path="/signupHouse"
            element={            
                <HouseholdSignup />         
            }
          />

<Route path="/paymentForm" element={<PaymentForm />} />






          <Route
            path="/login"
            element={user ? (
              isBusinessOrHouseholder(user.role) ? (
                <Navigate to="/user" replace />
              ) : (
                <Navigate to={`/${user.role}`} replace />
              )
            ) : (
              <Login onLogin={handleLogin} />
            )}
          />
          <Route
            path="/user/*"
            element={
              user && isBusinessOrHouseholder(user.role) ? (
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