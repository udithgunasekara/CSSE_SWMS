import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { QrCode, Map, ClipboardList, MessageSquare } from 'lucide-react';

const CollectorHome = ({ user }) => {
  const navigate = useNavigate(); // Hook to navigate programmatically

  const handleQrScan = () => {
    navigate('/collector/qr-scanner'); // Navigate to QR scanner
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Welcome, Collector {user.username}!</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Link to="/collector/qr-scanner" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <QrCode size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">QR Scanner</h2>
          <p className="text-gray-600">Scan QR codes to collect waste from bins</p>
        </Link>
        <Link to="/collector/bin-routes" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <ClipboardList size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Bin Routes</h2>
          <p className="text-gray-600">View and claim bin routes that need attention</p>
        </Link>
        <Link to="/collector/route-map" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <Map size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Route Map</h2>
          <p className="text-gray-600">View your selected bin route on an interactive map</p>
        </Link>
        <Link to="/collector/special-requests" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <MessageSquare size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Special Requests</h2>
          <p className="text-gray-600">View and respond to special waste collection requests</p>
        </Link>
      </div>

      {/* QR Scan Shortcut Button */}
      <button
        onClick={handleQrScan}
        className="mt-6 w-full py-4 rounded-full bg-green-600 text-white text-lg font-semibold shadow-lg hover:bg-green-700 transition-all duration-300 flex items-center justify-center"
      >
        <QrCode size={24} className="mr-2" />
        Open QR Scanner
      </button>
    </div>
  );
};

export default CollectorHome;
