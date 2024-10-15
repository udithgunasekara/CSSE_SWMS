import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';

const monthlyData = [
  { name: 'Jan', organic: 4000, recyclable: 2400, hazardous: 2400 },
  { name: 'Feb', organic: 3000, recyclable: 1398, hazardous: 2210 },
  { name: 'Mar', organic: 2000, recyclable: 9800, hazardous: 2290 },
  { name: 'Apr', organic: 2780, recyclable: 3908, hazardous: 2000 },
  { name: 'May', organic: 1890, recyclable: 4800, hazardous: 2181 },
  { name: 'Jun', organic: 2390, recyclable: 3800, hazardous: 2500 },
];

const wasteComposition = [
  { name: 'Organic', value: 45 },
  { name: 'Recyclable', value: 30 },
  { name: 'Hazardous', value: 15 },
  { name: 'Other', value: 10 },
];

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const WasteAnalytics = () => {
  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Waste Analytics</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Monthly Waste Collection by Type</h2>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={monthlyData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="organic" fill="#8884d8" name="Organic Waste" />
              <Bar dataKey="recyclable" fill="#82ca9d" name="Recyclable Waste" />
              <Bar dataKey="hazardous" fill="#ffc658" name="Hazardous Waste" />
            </BarChart>
          </ResponsiveContainer>
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Waste Composition</h2>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={wasteComposition}
                cx="50%"
                cy="50%"
                labelLine={false}
                outerRadius={80}
                fill="#8884d8"
                dataKey="value"
                label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
              >
                {wasteComposition.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>
      <div className="mt-6 bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">Key Metrics</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="border rounded-lg p-4">
            <h3 className="text-lg font-medium mb-2">Total Waste Collected</h3>
            <p className="text-3xl font-bold text-green-600">52,368 tons</p>
          </div>
          <div className="border rounded-lg p-4">
            <h3 className="text-lg font-medium mb-2">Recycling Rate</h3>
            <p className="text-3xl font-bold text-green-600">32.5%</p>
          </div>
          <div className="border rounded-lg p-4">
            <h3 className="text-lg font-medium mb-2">Waste Reduction</h3>
            <p className="text-3xl font-bold text-green-600">-5.2%</p>
            <p className="text-sm text-gray-500">Compared to last year</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WasteAnalytics;