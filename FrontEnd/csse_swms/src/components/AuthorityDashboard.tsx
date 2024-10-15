import React, { useState } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import {
  Home,
  BarChart2,
  Users,
  Map,
  ClipboardList,
  Settings,
  LogOut,
  Menu,
} from 'lucide-react';
import AuthorityHome from './authority/AuthorityHome';
import SystemPerformance from './authority/SystemPerformance';
import CollectorManagement from './authority/CollectorManagement';
import RouteManagement from './authority/RouteManagement';
import WasteAnalytics from './authority/WasteAnalytics';
import AreaManagementPage from './authority/AreaManagementPage';

const AuthorityDashboard = ({ user, onLogout }) => {
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
          WasteWise Authority
        </h2>
        <nav>
          <ul className="space-y-2">
            <li>
              <Link
                to="/authority"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Home className="mr-2" size={16} /> Home
              </Link>
            </li>
            <li>
              <Link
                to="/authority/performance"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <BarChart2 className="mr-2" size={16} /> System Performance
              </Link>
            </li>
            <li>
              <Link
                to="/authority/collectors"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Users className="mr-2" size={16} /> Collector Management
              </Link>
            </li>
            <li>
              <Link
                to="/authority/routes"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Map className="mr-2" size={16} /> Route Management
              </Link>
            </li>
            <li>
              <Link
                to="/authority/analytics"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <ClipboardList className="mr-2" size={16} /> Waste Analytics
              </Link>
            </li>
            <li>
              <Link
                to="/authority/area-management"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Settings className="mr-2" size={16} /> Area Management
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
            <Route path="/" element={<AuthorityHome user={user} />} />
            <Route path="/performance" element={<SystemPerformance />} />
            <Route path="/collectors" element={<CollectorManagement />} />
            <Route path="/routes" element={<RouteManagement />} />
            <Route path="/analytics" element={<WasteAnalytics />} />
            <Route path="/area-management" element={<AreaManagementPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
};

export default AuthorityDashboard;
