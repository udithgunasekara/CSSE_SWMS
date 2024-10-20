import React from 'react';
import { Trash2, Calendar, Clock, Weight } from 'lucide-react';

const HistoryTab = ({ scanHistory }) => (
  <div className="p-4">
    <h2 className="text-lg font-semibold mb-4">Collection History</h2>
    {scanHistory.length > 0 ? (
      <div className="space-y-3">
        {scanHistory.map((scan) => (
          <HistoryItem key={scan.historyID} scan={scan} />
        ))}
      </div>
    ) : (
      <div className="text-center text-gray-500 py-8">
        No collection history available
      </div>
    )}
  </div>
);

const HistoryItem = ({ scan }) => (
  <div className="p-3 rounded-lg bg-green-50">
    <div className="flex justify-between items-start">
      <div>
        <div className="flex items-center text-sm text-gray-600">
          <Trash2 size={16} className="mr-2" />
          Tag ID: {scan.tagid}
        </div>
        <div className="flex items-center text-sm text-gray-600 mt-1">
          <Calendar size={16} className="mr-2" />
          Date: {scan.date}
        </div>
        <div className="flex items-center text-sm text-gray-600 mt-1">
          <Clock size={16} className="mr-2" />
          Time: {scan.time}
        </div>
      </div>
      <div className="text-right">
        <div className="flex items-center text-sm text-gray-600">
          <Weight size={16} className="mr-2" />
          Weight: {scan.weight} kg
        </div>
      </div>
    </div>
  </div>
);

export default HistoryTab;