import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const data = [
  { name: 'Jan', wasteCollected: 4000, recyclingRate: 2400 },
  { name: 'Feb', wasteCollected: 3000, recyclingRate: 1398 },
  { name: 'Mar', wasteCollected: 2000, recyclingRate: 9800 },
  { name: 'Apr', wasteCollected: 2780, recyclingRate: 3908 },
  { name: 'May', wasteCollected: 1890, recyclingRate: 4800 },
  { name: 'Jun', wasteCollected: 2390, recyclingRate: 3800 },
];

const SystemPerformance = () => {
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">System Performance</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">Waste Collection and Recycling Trends</h2>
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis yAxisId="left" orientation="left" stroke="#8884d8" />
            <YAxis yAxisId="right" orientation="right" stroke="#82ca9d" />
            <Tooltip />
            <Legend />
            <Bar yAxisId="left" dataKey="wasteCollected" fill="#8884d8" name="Waste Collected (tons)" />
            <Bar yAxisId="right" dataKey="recyclingRate" fill="#82ca9d" name="Recycling Rate (%)" />
          </BarChart>
        </ResponsiveContainer>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold mb-2">Total Waste Collected</h3>
          <p className="text-3xl font-bold text-green-600">15,060 tons</p>
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold mb-2">Average Recycling Rate</h3>
          <p className="text-3xl font-bold text-green-600">32.5%</p>
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h3 className="text-lg font-semibold mb-2">Active Collectors</h3>
          <p className="text-3xl font-bold text-green-600">42</p>
        </div>
      </div>
    </div>
  );
};

export default SystemPerformance;