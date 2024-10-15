import React from 'react';
import { Link } from 'react-router-dom';
import { BarChart2, Users, Map, ClipboardList } from 'lucide-react';

const AuthorityHome = ({ user }) => {
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Welcome, {user.username} (Waste Management Authority)</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Link to="/authority/performance" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <BarChart2 size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">System Performance</h2>
          <p className="text-gray-600">Monitor overall waste management system performance</p>
        </Link>
        <Link to="/authority/collectors" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <Users size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Collector Management</h2>
          <p className="text-gray-600">Manage waste collectors and their assignments</p>
        </Link>
        <Link to="/authority/routes" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <Map size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Route Management</h2>
          <p className="text-gray-600">Optimize and manage waste collection routes</p>
        </Link>
        <Link to="/authority/analytics" className="bg-white shadow rounded-lg p-6 hover:shadow-lg transition-shadow">
          <ClipboardList size={48} className="text-green-600 mb-4" />
          <h2 className="text-xl font-semibold mb-2">Waste Analytics</h2>
          <p className="text-gray-600">Analyze waste collection data and trends</p>
        </Link>
      </div>
    </div>
  );
};

export default AuthorityHome;