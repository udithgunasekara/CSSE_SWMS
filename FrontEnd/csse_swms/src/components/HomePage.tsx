import React from 'react';
import { Trash2, Recycle, TrendingUp } from 'lucide-react';

const HomePage = () => {
  return (
    <div className="space-y-6">
      <h1 className="text-4xl font-bold text-green-800">Welcome to WasteWise</h1>
      <p className="text-xl text-gray-600">Empowering communities through efficient waste management</p>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <Trash2 size={48} className="text-green-600 mb-4" />
          <h2 className="text-2xl font-semibold mb-2">Waste Collection</h2>
          <p className="text-gray-600">Optimize routes and schedules for efficient waste collection across all areas.</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <Recycle size={48} className="text-green-600 mb-4" />
          <h2 className="text-2xl font-semibold mb-2">Recycling Initiatives</h2>
          <p className="text-gray-600">Promote and track recycling programs to reduce landfill waste.</p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <TrendingUp size={48} className="text-green-600 mb-4" />
          <h2 className="text-2xl font-semibold mb-2">Data-Driven Insights</h2>
          <p className="text-gray-600">Analyze waste management data to make informed decisions and improvements.</p>
        </div>
      </div>

      <div className="mt-12">
        <h2 className="text-2xl font-bold text-green-800 mb-4">Latest Updates</h2>
        <ul className="space-y-2">
          <li className="bg-white p-4 rounded-lg shadow">
            <span className="font-semibold">New Recycling Center:</span> Opening next month in the downtown area.
          </li>
          <li className="bg-white p-4 rounded-lg shadow">
            <span className="font-semibold">Waste Reduction Campaign:</span> Launching community-wide initiative next week.
          </li>
          <li className="bg-white p-4 rounded-lg shadow">
            <span className="font-semibold">Collection Schedule Change:</span> Updated routes for more efficient pickups.
          </li>
        </ul>
      </div>
    </div>
  );
};

export default HomePage;