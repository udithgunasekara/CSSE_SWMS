import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const data = [
  { name: 'Downtown', waste: 4000, recycling: 2400 },
  { name: 'Suburbs', waste: 3000, recycling: 1398 },
  { name: 'Industrial', waste: 2000, recycling: 9800 },
  { name: 'Residential', waste: 2780, recycling: 3908 },
  { name: 'Commercial', waste: 1890, recycling: 4800 },
];

const StatisticsPage = () => {
  return (
    <div className="space-y-6">
      <h1 className="text-4xl font-bold text-green-800">Waste Collection Statistics</h1>
      
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-2xl font-semibold mb-4">Waste Collection by Area</h2>
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="waste" fill="#8884d8" name="Waste" />
            <Bar dataKey="recycling" fill="#82ca9d" name="Recycling" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold mb-4">High Waste Areas</h2>
          <ul className="space-y-2">
            {data.sort((a, b) => b.waste - a.waste).slice(0, 3).map((area, index) => (
              <li key={index} className="flex justify-between items-center">
                <span>{area.name}</span>
                <span className="font-semibold text-red-600">{area.waste} tons</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold mb-4">Top Recycling Areas</h2>
          <ul className="space-y-2">
            {data.sort((a, b) => b.recycling - a.recycling).slice(0, 3).map((area, index) => (
              <li key={index} className="flex justify-between items-center">
                <span>{area.name}</span>
                <span className="font-semibold text-green-600">{area.recycling} tons</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default StatisticsPage;