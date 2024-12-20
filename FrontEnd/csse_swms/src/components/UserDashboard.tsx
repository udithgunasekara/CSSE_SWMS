import React, { useState } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import { Home, Trash2, ClipboardList, LogOut, Menu } from 'lucide-react';
import UserHome from './user/UserHome';
import SpecialRequests from './user/SpecialRequests';
import RequestStatus from './user/RequestStatus';

const UserDashboard = ({ user, onLogout }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div className="flex flex-col h-screen bg-gray-100 md:flex-row">
      <aside
        className={`${
          isMenuOpen ? 'block' : 'hidden'
        } w-full bg-green-800 text-white p-4 md:block md:w-64 md:p-6`}
      >
        <h2 className="text-xl font-bold mb-4 md:text-2xl md:mb-6">
          WasteWise User
        </h2>
        <nav>
          <ul className="space-y-2">
            <li>
              <Link
                to="/user"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Home className="mr-2" size={16} /> Home
              </Link>
            </li>
            <li>
              <Link
                to="/user/special-requests"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Trash2 className="mr-2" size={16} /> Special Requests
              </Link>
            </li>
            <li>
              <Link
                to="/user/request-status"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <ClipboardList className="mr-2" size={16} /> Request Status
              </Link>
            </li>
          </ul>
        </nav>
        <button
          onClick={() => {
            onLogout();
            setIsMenuOpen(false);
          }}
          className="flex items-center py-2 px-4 rounded hover:bg-green-700 mt-4 text-sm md:text-base md:mt-auto"
        >
          <LogOut className="mr-2" size={16} /> Logout
        </button>
      </aside>
      <div className="flex-1 flex flex-col">
        <header className="bg-green-800 text-white p-4 flex justify-between items-center md:hidden">
          <h1 className="text-xl font-bold">WasteWise</h1>
          <button
            onClick={toggleMenu}
            className="text-white focus:outline-none"
          >
            <Menu size={24} />
          </button>
        </header>
        <main className="flex-1 p-4 md:p-8 overflow-y-auto">
          <Routes>
            <Route path="/" element={<UserHome user={user} />} />
            <Route path="/special-requests" element={<SpecialRequests />} />
            <Route path="/request-status" element={<RequestStatus />} />
          </Routes>
        </main>
      </div>
    </div>
  );
};

export default UserDashboard;
