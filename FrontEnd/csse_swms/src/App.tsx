import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom';
import { LoadScript } from '@react-google-maps/api'; // Import LoadScript
import Login from './components/Login';
import UserDashboard from './components/UserDashboard';
import CollectorDashboard from './components/CollectorDashboard';
import AuthorityDashboard from './components/AuthorityDashboard';

function App() {

  // Test CORS: Fetch data from the Spring Boot backend
  useEffect(() => {
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

  const [user, setUser] = useState(null);

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <LoadScript googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY || ''}>
      <Router>
        <div className="min-h-screen bg-gray-100">
          <Routes>
            {/* Login Route */}
            <Route path="/login" element={<Login onLogin={handleLogin} />} />

            {/* User Dashboard Route */}
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

            {/* Collector Dashboard Route */}
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

            {/* Authority Dashboard Route */}
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

            {/* Catch-all Route */}
            <Route path="*" element={<Navigate to="/login" replace />} />
          </Routes>
        </div>
      </Router>
    </LoadScript>
  );
}

export default App;