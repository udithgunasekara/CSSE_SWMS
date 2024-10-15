import React from 'react';

const RequestStatus = () => {
  // Mock data for demonstration
  const requests = [
    { id: 1, type: 'Large Item Pickup', status: 'Pending', date: '2023-04-15' },
    { id: 2, type: 'Hazardous Waste', status: 'In Progress', date: '2023-04-10' },
    { id: 3, type: 'Electronic Waste', status: 'Completed', date: '2023-04-05' },
  ];

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Request Status</h1>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {requests.map((request) => (
              <tr key={request.id}>
                <td className="px-6 py-4 whitespace-nowrap">{request.id}</td>
                <td className="px-6 py-4 whitespace-nowrap">{request.type}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                    request.status === 'Completed' ? 'bg-green-100 text-green-800' :
                    request.status === 'In Progress' ? 'bg-yellow-100 text-yellow-800' :
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {request.status}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">{request.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RequestStatus;