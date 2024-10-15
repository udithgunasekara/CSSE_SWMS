import React from 'react';
import { Trash2, Bell, Calendar } from 'lucide-react';

const UserHome = ({ user }) => {
  // Safely access user properties
  const username = user?.username || 'User';

  // Mock data for demonstration
  const recentRequests = [
    {
      id: 1,
      type: 'Large Item Pickup',
      status: 'Scheduled',
      date: '2023-04-25',
    },
    {
      id: 2,
      type: 'Recycling Bin Request',
      status: 'Completed',
      date: '2023-04-10',
    },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold mb-6">Welcome, {username}!</h1>

      <div className="bg-white border rounded-lg p-4">
        <h2 className="text-lg font-semibold mb-4">Recent Requests</h2>
        <div className="space-y-4">
          {recentRequests.map((request) => (
            <div key={request.id} className="flex items-center justify-between">
              <div className="space-y-1">
                <p className="text-sm font-medium">{request.type}</p>
                <div className="flex items-center space-x-2 text-xs text-gray-500">
                  <Calendar className="h-4 w-4" />
                  <span>{request.date}</span>
                </div>
              </div>
              <span
                className={`px-2 py-1 rounded-full text-xs ${
                  request.status === 'Scheduled'
                    ? 'bg-blue-100 text-blue-800'
                    : 'bg-green-100 text-green-800'
                }`}
              >
                {request.status}
              </span>
            </div>
          ))}
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="bg-white border rounded-lg p-4">
          <h2 className="text-lg font-semibold flex items-center mb-4">
            <Trash2 className="h-5 w-5 mr-2 text-green-600" />
            Request Pickup
          </h2>
          <p className="text-sm text-gray-600 mb-4">
            Need a waste pickup? Submit a request here.
          </p>
          <button className="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 transition-colors">
            Submit Request
          </button>
        </div>

        <div className="bg-white border rounded-lg p-4">
          <h2 className="text-lg font-semibold flex items-center mb-4">
            <Bell className="h-5 w-5 mr-2 text-green-600" />
            Recycling Tips
          </h2>
          <p className="text-sm text-gray-600 mb-4">
            Learn how to improve your recycling habits and reduce waste.
          </p>
          <button className="w-full bg-green-100 text-green-800 py-2 px-4 rounded-md hover:bg-green-200 transition-colors">
            View Tips
          </button>
        </div>
      </div>
    </div>
  );
};

export default UserHome;
