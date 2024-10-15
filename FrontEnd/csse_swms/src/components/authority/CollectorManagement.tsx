import React, { useState } from 'react';

const CollectorManagement = () => {
  const [collectors, setCollectors] = useState([
    { id: 1, name: 'John Doe', area: 'Downtown', status: 'Active' },
    { id: 2, name: 'Jane Smith', area: 'Suburb A', status: 'Active' },
    { id: 3, name: 'Mike Johnson', area: 'Industrial Park', status: 'Inactive' },
  ]);

  const [newCollector, setNewCollector] = useState({ name: '', area: '', status: 'Active' });

  const handleAddCollector = (e) => {
    e.preventDefault();
    setCollectors([...collectors, { ...newCollector, id: collectors.length + 1 }]);
    setNewCollector({ name: '', area: '', status: 'Active' });
  };

  const handleStatusChange = (id, newStatus) => {
    setCollectors(collectors.map(collector => 
      collector.id === id ? { ...collector, status: newStatus } : collector
    ));
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Collector Management</h1>
      <div className="bg-white shadow rounded-lg p-6 mb-6">
        <h2 className="text-xl font-semibold mb-4">Add New Collector</h2>
        <form onSubmit={handleAddCollector} className="space-y-4">
          <div>
            <label htmlFor="name" className="block text-sm font-medium text-gray-700">Name</label>
            <input
              type="text"
              id="name"
              value={newCollector.name}
              onChange={(e) => setNewCollector({ ...newCollector, name: e.target.value })}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
              required
            />
          </div>
          <div>
            <label htmlFor="area" className="block text-sm font-medium text-gray-700">Area</label>
            <input
              type="text"
              id="area"
              value={newCollector.area}
              onChange={(e) => setNewCollector({ ...newCollector, area: e.target.value })}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-green-500 focus:ring focus:ring-green-200"
              required
            />
          </div>
          <button
            type="submit"
            className="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50"
          >
            Add Collector
          </button>
        </form>
      </div>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Area</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Action</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {collectors.map((collector) => (
              <tr key={collector.id}>
                <td className="px-6 py-4 whitespace-nowrap">{collector.name}</td>
                <td className="px-6 py-4 whitespace-nowrap">{collector.area}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                    collector.status === 'Active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  }`}>
                    {collector.status}
                  </span>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <select
                    value={collector.status}
                    onChange={(e) => handleStatusChange(collector.id, e.target.value)}
                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500 sm:text-sm"
                  >
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CollectorManagement;