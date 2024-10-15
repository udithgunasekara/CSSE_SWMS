import React, { useState, useEffect } from 'react';
import { Routes, Route, Link, useLocation } from 'react-router-dom';
import { Home, QrCode, Map, ClipboardList, MessageSquare, LogOut, Menu, X } from 'lucide-react';
import CollectorHome from './CollectorHome';
import QRScanner from './QRScanner';
import BinRoutes from './BinRoutes';
import RouteMap from './RouteMap';
import SpecialRequests from './SpecialRequests';
import ErrorBoundary from '../ErrorBoundary';

const CollectorDashboard = ({ user, onLogout }) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const location = useLocation();

  const toggleSidebar = () => setIsSidebarOpen(!isSidebarOpen);

  useEffect(() => {
    setIsSidebarOpen(false);
  }, [location]);

  const menuItems = [
    { path: "/collector", icon: Home, text: "Home" },
    { path: "/collector/qr-scanner", icon: QrCode, text: "QR Scanner" },
    { path: "/collector/bin-routes", icon: ClipboardList, text: "Bin Routes" },
    { path: "/collector/route-map", icon: Map, text: "Route Map" },
    { path: "/collector/special-requests", icon: MessageSquare, text: "Special Requests" },
  ];

  return (
    <div className="flex flex-col h-screen bg-gray-100">
      <header className="bg-green-800 text-white p-4 flex justify-between items-center md:hidden">
        <h1 className="text-xl font-bold">WasteWise Collector</h1>
        <button onClick={toggleSidebar} className="focus:outline-none p-2">
          {isSidebarOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </header>
      <div className="flex flex-1 overflow-hidden">
        <aside className={`w-64 bg-green-800 text-white p-6 flex-shrink-0 transition-all duration-300 ease-in-out ${isSidebarOpen ? 'translate-x-0' : '-translate-x-full'} md:translate-x-0 fixed md:static h-full z-10 ${isSidebarOpen ? 'mobile-menu' : ''}`}>
          <h2 className="text-2xl font-bold mb-6 hidden md:block">WasteWise Collector</h2>
          <nav>
            <ul className="space-y-2">
              {menuItems.map((item) => (
                <li key={item.path}>
                  <Link to={item.path} className="flex items-center py-2 px-4 rounded hover:bg-green-700 transition-colors duration-200">
                    <item.icon className="mr-2" size={20} />
                    {item.text}
                  </Link>
                </li>
              ))}
            </ul>
          </nav>
          <button
            onClick={onLogout}
            className="flex items-center py-2 px-4 rounded hover:bg-green-700 mt-auto transition-colors duration-200"
          >
            <LogOut className="mr-2" size={20} />
            Logout
          </button>
        </aside>
        <main className="flex-1 p-4 md:p-8 overflow-auto">
          <Routes>
            <Route path="/" element={<CollectorHome user={user} />} />
            <Route path="/qr-scanner" element={<QRScanner />} />
            <Route path="/bin-routes" element={<BinRoutes />} />
            <Route path="/route-map" element={
              <ErrorBoundary>
                <RouteMap />
              </ErrorBoundary>
            } />
            <Route path="/special-requests" element={<SpecialRequests />} />
          </Routes>
        </main>
      </div>
    </div>
  );
};

export default CollectorDashboard;