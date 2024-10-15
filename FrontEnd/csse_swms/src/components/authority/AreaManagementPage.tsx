import React, { useState } from 'react';

const AreaManagementPage = () => {
  const [cities, setCities] = useState([
    { id: 1, name: 'New York', model: 'Urban' },
    { id: 2, name: 'Los Angeles', model: 'Suburban' },
    { id: 3, name: 'Chicago', model: 'Mixed' },
  ]);

  const [facilities, setFacilities] = useState([
    {
      id: 1,
      name: 'Recycling Center A',
      address: '123 Green St',
      latitude: 40.7128,
      longitude: -74.006,
    },
    {
      id: 2,
      name: 'Waste Treatment Plant B',
      address: '456 Clean Ave',
      latitude: 34.0522,
      longitude: -118.2437,
    },
  ]);

  return (
    <div className="p-4">
      <h1 className="text-2xl md:text-3xl font-bold mb-6">Area Management</h1>

      <div className="space-y-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">Cities</h2>
            <button className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 w-full sm:w-auto">
              Add City
            </button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    City Name
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Model
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {cities.map((city) => (
                  <tr key={city.id}>
                    <td className="px-6 py-4 whitespace-nowrap">{city.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <select className="border rounded p-1">
                        <option value="Urban" selected={city.model === 'Urban'}>
                          Urban
                        </option>
                        <option
                          value="Suburban"
                          selected={city.model === 'Suburban'}
                        >
                          Suburban
                        </option>
                        <option value="Rural" selected={city.model === 'Rural'}>
                          Rural
                        </option>
                        <option value="Mixed" selected={city.model === 'Mixed'}>
                          Mixed
                        </option>
                      </select>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <button className="text-blue-500 hover:text-blue-700 mr-2">
                        Edit
                      </button>
                      <button className="text-red-500 hover:text-red-700">
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">
              Processing Facilities
            </h2>
            <button className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 w-full sm:w-auto">
              Add Facility
            </button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Facility
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Address
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Location
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {facilities.map((facility) => (
                  <tr key={facility.id}>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.name}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.address}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.latitude}, {facility.longitude}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <button className="text-blue-500 hover:text-blue-700 mr-2">
                        Edit
                      </button>
                      <button className="text-red-500 hover:text-red-700">
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AreaManagementPage;
