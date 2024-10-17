import axios from 'axios';
import React, { useEffect } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';

interface MonthlyWasteDto {
  month: string;
  wasteStats: {
    'Organic ': number;
    'Hazardous ': number;
    'Recyclable ': number;
  };
}

interface FormattedMonthlyData {
  name: string;
  organic: number;
  recyclable: number;
  hazardous: number;
}

interface TrashbinInfo{
  trashbinId: string;
  type: string;
  weight: number;
  level: number;
}

interface WasteCompositionData {
  name: string;
  value: number;
}

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const WasteAnalytics = () => {
  const [wasteComposition, setWasteComposition] = React.useState<WasteCompositionData[]>([]);
  const [monthlyData, setMonthlyData] = React.useState<FormattedMonthlyData[]>([]);
  const [trashbinInfo, setTrashbinInfo] = React.useState<TrashbinInfo[]>([]);
  const [pastWeekWaste, setPastWeekWaste] = React.useState<number[]>([]);
  const [totalBins, setTotalBins] = React.useState<number>(0);
  const [totalManpower, setTotalManpower] = React.useState<number>(0);
  const [isloading, setIsLoading] = React.useState<boolean>(true);
  const [error, setError] = React.useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [compositionResponse, binInfoResponse,pasteWeekWasteResponse,totalBinsResponse,totalManpowerResponse,monthlyResponse] = await Promise.all([
          fetch('http://localhost:8081/api/stat'),
          fetch('http://localhost:8081/api/stat/bininfo'),
          fetch('http://localhost:8081/api/stat/totalfortheweek'),
          fetch('http://localhost:8081/api/stat/totalbins'),
          fetch('http://localhost:8081/api/stat/manpower'),
          fetch('http://localhost:8081/api/stat/graphdata')
        ]);

        if (
          !compositionResponse.ok || !binInfoResponse.ok ||
          !pasteWeekWasteResponse.ok || !totalBinsResponse.ok||
          !totalManpowerResponse.ok || !monthlyResponse.ok) 
        {
          throw new Error('Failed to fetch data');
        }

        const compositionData: Record<string, number> = await compositionResponse.json();
        const binData: TrashbinInfo[] = await binInfoResponse.json();
        const pastWeekWasteData: number[] = await pasteWeekWasteResponse.json();
        const totalBinsData: number = await totalBinsResponse.json();
        const totalManpowerData: number = await totalManpowerResponse.json();
        const monthlyWasteData: MonthlyWasteDto[] = await monthlyResponse.json();

        const formattedMonthlyData: FormattedMonthlyData[] = monthlyWasteData.map(month => ({
          name: month.month.slice(0, 3), // Take first 3 letters of month name
          organic: month.wasteStats['Organic '],
          recyclable: month.wasteStats['Recyclable '],
          hazardous: month.wasteStats['Hazardous ']
        }));

        const transformedData = Object.entries(compositionData).map(([name, value]) => ({
          name: name.charAt(0).toUpperCase() + name.slice(1),
          value: Number(value)
        }));

        setWasteComposition(transformedData);
        setPastWeekWaste(pastWeekWasteData);
        setTrashbinInfo(binData);
        setTotalBins(totalBinsData);
        setMonthlyData(formattedMonthlyData);
        setTotalManpower(totalManpowerData);
        setIsLoading(false);
      } catch (error) {
        setError(error instanceof Error ? error.message : 'An error occurred');
        setIsLoading(false);
      }
    };
    fetchData();
  }, []);

  if (isloading) {
    return <div className="text-center p-4">Loading Statistics data...</div>;
  }

  if (error) {
    return <div className="text-center p-4 text-red-500">Error: {error}</div>;
  }

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
                outerRadius={100}
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
            <h3 className="text-lg font-medium mb-2">Total Waste Collected for past 7 days</h3>
            <p className="text-3xl font-bold text-green-600">{pastWeekWaste} Kg</p>
          </div>
          <div className="border rounded-lg p-4">
            <h3 className="text-lg font-medium mb-2">Total Connected SmartBins</h3>
            <p className="text-3xl font-bold text-green-600">{totalBins}</p>
          </div>
          <div className="border rounded-lg p-4">
            <h3 className="text-lg font-medium mb-2">Total Collection Manpower</h3>
            <p className="text-3xl font-bold text-green-600">{totalManpower} persons</p>
          </div>
        </div>
      </div>
      <div className="mt-6 bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">Trash Bin Status</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bin ID</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Weight (kg)</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fill Level (%)</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {trashbinInfo.map((bin) => (
                <tr key={bin.trashbinId}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{bin.trashbinId}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 capitalize">{bin.type}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{bin.weight.toFixed(1)}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{bin.level.toFixed(1)}%</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span 
                      className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                        bin.level >= 90 
                          ? 'bg-red-100 text-red-800' 
                          : bin.level >= 70 
                          ? 'bg-yellow-100 text-yellow-800' 
                          : 'bg-green-100 text-green-800'
                      }`}
                    >
                      {bin.level >= 90 ? 'Critical' : bin.level >= 70 ? 'Warning' : 'Normal'}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default WasteAnalytics;