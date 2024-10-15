import React, { useState } from 'react';

const SpecialRequests = () => {
  const [requests, setRequests] = useState([
    { id: 1, type: 'Large Item Pickup', address: '123 Main St', status: 'Pending' },
    { id: 2, type: 'Hazardous Waste', address: '456 Elm St', status: 'Accepted' },
    { id: 3, type: 'Electronic Waste', address: '789 Oak St', status: 'Completed' },
  ]);

  const handleStatusChange = (id, newStatus) => {
    setRequests(requests.map(request => 
      request.id === id ? { ...request, status: newStatus } : request
    ));
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'Completed': return 'bg-green-100 text-green-800';
      case 'Accepted': return 'bg-blue-100 text-blue-800';
      default: return 'bg-yellow-100 text-yellow-800';
    }
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Special Requests</h1>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <ul className="divide-y divide-gray-200">
          {requests.map((request) => (
            <li key={request.id} className="p-4">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between">
                <div className="mb-2 sm:mb-0">
                  <h3 className="text-lg font-medium">{request.type}</h3>
                  <p className="text-sm text-gray-500">{request.address}</p>
                </div>
                <div className="flex items-center">
                  <span className={`px-2 py-1 text-xs font-semibold rounded-full ${getStatusColor(request.status)}`}>
                    {request.status}
                  </span>
                  <select
                    value={request.status}
                    onChange={(e) => handleStatusChange(request.id, e.target.value)}
                    className="ml-2 block w-full sm:w-auto py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500 sm:text-sm"
                  >
                    <option value="Pending">Pending</option>
                    <option value="Accepted">Accepted</option>
                    <option value="Completed">Completed</option>
                  </select>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default SpecialRequests;