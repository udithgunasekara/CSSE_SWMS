import React, { useState } from 'react';
import { ChevronDown, ChevronUp } from 'lucide-react';

const BinRoutes = () => {
  const [routes, setRoutes] = useState([
    { id: 1, area: 'Downtown', bins: 15, avgCapacity: '75%', claimed: false },
    { id: 2, area: 'Suburb A', bins: 10, avgCapacity: '60%', claimed: false },
    { id: 3, area: 'Industrial Park', bins: 20, avgCapacity: '85%', claimed: false },
  ]);

  const [expandedRoute, setExpandedRoute] = useState(null);

  const handleClaim = (id) => {
    setRoutes(routes.map(route => 
      route.id === id ? { ...route, claimed: true } : route
    ));
  };

  const toggleExpand = (id) => {
    setExpandedRoute(expandedRoute === id ? null : id);
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Bin Routes</h1>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <ul className="divide-y divide-gray-200">
          {routes.map((route) => (
            <li key={route.id} className="p-4">
              <div className="flex justify-between items-center">
                <div className="flex-1">
                  <h3 className="text-lg font-medium">{route.area}</h3>
                  <p className="text-sm text-gray-500">{route.bins} bins | Avg. Capacity: {route.avgCapacity}</p>
                </div>
                <div className="flex items-center">
                  {route.claimed ? (
                    <span className="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                      Claimed
                    </span>
                  ) : (
                    <button
                      onClick={() => handleClaim(route.id)}
                      className="bg-green-600 text-white py-2 px-4 rounded-md text-sm hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 transition-colors duration-200"
                    >
                      Claim
                    </button>
                  )}
                  <button
                    onClick={() => toggleExpand(route.id)}
                    className="ml-2 p-2 rounded-full hover:bg-gray-100 focus:outline-none transition-colors duration-200"
                  >
                    {expandedRoute === route.id ? <ChevronUp size={20} /> : <ChevronDown size={20} />}
                  </button>
                </div>
              </div>
              {expandedRoute === route.id && (
                <div className="mt-4 text-sm">
                  <p>Additional details about the route can be displayed here.</p>
                  <p>For example, specific bin locations, collection times, etc.</p>
                </div>
              )}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default BinRoutes;