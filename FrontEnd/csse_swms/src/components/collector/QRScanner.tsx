import React, { useState } from 'react';
import { QrCode, Camera } from 'lucide-react';

const QRScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [isScanning, setIsScanning] = useState(false);

  const handleScan = () => {
    setIsScanning(true);
    // Simulating a QR code scan with a delay
    setTimeout(() => {
      const mockData = {
        binId: 'BIN-' + Math.floor(1000 + Math.random() * 9000),
        location: 'Lat: 40.7128, Long: -74.0060',
        capacity: Math.floor(Math.random() * 100) + '%'
      };
      setScannedData(mockData);
      setIsScanning(false);
    }, 2000);
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">QR Scanner</h1>
      <div className="bg-white shadow rounded-lg p-4">
        <div className="flex flex-col items-center mb-4">
          {isScanning ? (
            <div className="relative w-64 h-64 bg-gray-200 rounded-lg overflow-hidden">
              <Camera size={64} className="text-gray-400 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2" />
              <div className="absolute inset-0 flex items-center justify-center">
                <div className="w-48 h-48 border-4 border-green-500 animate-pulse"></div>
              </div>
            </div>
          ) : (
            <QrCode size={100} className="text-green-600 mb-4" />
          )}
          <button
            onClick={handleScan}
            disabled={isScanning}
            className={`mt-4 bg-green-600 text-white py-3 px-6 rounded-md text-lg font-semibold hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 transition-colors duration-200 ${isScanning ? 'opacity-50 cursor-not-allowed' : ''}`}
          >
            {isScanning ? 'Scanning...' : 'Scan QR Code'}
          </button>
        </div>
        {scannedData && (
          <div className="mt-6 bg-gray-100 rounded-lg p-4">
            <h2 className="text-lg font-semibold mb-2">Scanned Bin Data:</h2>
            <ul className="space-y-2 text-sm">
              <li><strong>Bin ID:</strong> {scannedData.binId}</li>
              <li><strong>Location:</strong> {scannedData.location}</li>
              <li><strong>Current Capacity:</strong> {scannedData.capacity}</li>
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default QRScanner;