import React, { useState } from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import {
  Home,
  QrCode,
  Map,
  ClipboardList,
  MessageSquare,
  LogOut,
  Menu,
} from 'lucide-react';
import CollectorHome from './collector/CollectorHome';
import QRScanner from './collector/QRScanner';
import BinRoutes from './collector/BinRoutes';
import RouteMap from './collector/RouteMap';
import SpecialRequests from './collector/SpecialRequests';

const CollectorDashboard = ({ user, onLogout }) => {
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
          WasteWise Collector
        </h2>
        <nav>
          <ul className="space-y-2">
            <li>
              <Link
                to="/collector"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Home className="mr-2" size={16} /> Home
              </Link>
            </li>
            <li>
              <Link
                to="/collector/qr-scanner"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <QrCode className="mr-2" size={16} /> QR Scanner
              </Link>
            </li>
            <li>
              <Link
                to="/collector/bin-routes"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <ClipboardList className="mr-2" size={16} /> Bin Routes
              </Link>
            </li>
            <li>
              <Link
                to="/collector/route-map"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <Map className="mr-2" size={16} /> Route Map
              </Link>
            </li>
            <li>
              <Link
                to="/collector/special-requests"
                className="flex items-center py-2 px-4 rounded hover:bg-green-700 text-sm md:text-base"
                onClick={() => setIsMenuOpen(false)}
              >
                <MessageSquare className="mr-2" size={16} /> Special Requests
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
            <Route path="/" element={<CollectorHome user={user} />} />
            <Route path="/qr-scanner" element={<QRScanner />} />
            <Route path="/bin-routes" element={<BinRoutes />} />
            <Route path="/route-map" element={<RouteMap />} />
            <Route path="/special-requests" element={<SpecialRequests />} />
          </Routes>
        </main>
      </div>
    </div>
  );
};

export default CollectorDashboard;
