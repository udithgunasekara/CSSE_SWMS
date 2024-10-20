import React, { useState, useEffect } from 'react';
import { Camera, History, HelpCircle } from 'lucide-react';
import ScanTab from './ScanTab';
import HistoryTab from './HistoryTab';
import HelpTab from './HelpTab';
import StatusMessage from './StatusMessage';
import { fetchUserHistory } from '../../utils/api';
import { getTodayCollections, getTotalWeight } from '../../utils/utils';

const OptimizedQRScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [updateStatus, setUpdateStatus] = useState(null);
  const [scanHistory, setScanHistory] = useState([]);
  const [selectedTab, setSelectedTab] = useState('scan');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    fetchUserHistory().then(setScanHistory);
  }, []);

  const handleTabChange = (tab) => {
    setSelectedTab(tab);
    if (tab === 'history') {
      fetchUserHistory().then(setScanHistory);
    }
    setUpdateStatus(null);
    setScannedData(null);
  };

  return (
    <div className="flex flex-col items-center justify-start min-h-screen bg-gray-50 p-4">
      <div className="w-full max-w-md">
        {/* Header with Stats */}
        <div className="bg-green-600 text-white p-4 rounded-t-lg">
          <h1 className="text-2xl font-bold text-center mb-2">Waste Collection Scanner</h1>
          <div className="flex justify-around text-sm">
            <div className="text-center">
              <div className="font-bold">Today's Collections</div>
              <div>{getTodayCollections(scanHistory)}</div>
            </div>
            <div className="text-center">
              <div className="font-bold">Total Weight</div>
              <div>{getTotalWeight(scanHistory)} kg</div>
            </div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="bg-white border-b flex justify-around p-2">
          <TabButton icon={Camera} label="Scan" tab="scan" selectedTab={selectedTab} onClick={handleTabChange} />
          <TabButton icon={History} label="History" tab="history" selectedTab={selectedTab} onClick={handleTabChange} />
          <TabButton icon={HelpCircle} label="Help" tab="help" selectedTab={selectedTab} onClick={handleTabChange} />
        </div>

        {/* Main Content */}
        <div className="bg-white shadow-lg rounded-b-lg overflow-hidden">
          {selectedTab === 'scan' && (
            <ScanTab
              setScannedData={setScannedData}
              setUpdateStatus={setUpdateStatus}
              isLoading={isLoading}
              setIsLoading={setIsLoading}
            />
          )}
          {selectedTab === 'history' && <HistoryTab scanHistory={scanHistory} />}
          {selectedTab === 'help' && <HelpTab />}

          {/* Status Messages */}
          {updateStatus && <StatusMessage status={updateStatus} />}
        </div>
      </div>
    </div>
  );
};

const TabButton = ({ icon: Icon, label, tab, selectedTab, onClick }) => (
  <button
    onClick={() => onClick(tab)}
    className={`flex items-center px-4 py-2 rounded-lg ${
      selectedTab === tab ? 'bg-green-100 text-green-700' : 'text-gray-600'
    }`}
  >
    <Icon size={18} className="mr-2" />
    {label}
  </button>
);

export default OptimizedQRScanner;