import React, { useState, useCallback } from 'react';
import QrReader from 'react-qr-scanner';
import { QrCode, Camera, Loader2, XCircle } from 'lucide-react';
import { handleWasteCollection } from '../../utils/api';

const ScanTab = ({ setScannedData, setUpdateStatus, isLoading, setIsLoading }) => {
  const [isScanning, setIsScanning] = useState(false);

  const handleScan = useCallback((data) => {
    if (data) {
      try {
        const parsedData = JSON.parse(data.text);
        setScannedData(parsedData);
        setIsScanning(false);
        
        if (!parsedData.trashbinId || !parsedData.userid) {
          throw new Error('Invalid QR code: Missing required fields');
        }
        
        handleWasteCollection(parsedData.trashbinId, parsedData.userid, setUpdateStatus, setIsLoading);
      } catch (error) {
        setUpdateStatus({ 
          success: false, 
          message: 'Invalid QR code format. Expected format: {"trashbinId": "...", "userId": "..."}' 
        });
        setIsScanning(false);
      }
    }
  }, [setScannedData, setUpdateStatus, setIsLoading]);

  const handleError = useCallback((err) => {
    setUpdateStatus({ 
      success: false, 
      message: 'Error accessing camera. Please check camera permissions.' 
    });
    setIsScanning(false);
  }, [setUpdateStatus]);

  return (
    <div className="p-4">
      {isScanning ? (
        <ScannerView handleError={handleError} handleScan={handleScan} />
      ) : (
        <IdleView />
      )}

      <ScanButton 
        isScanning={isScanning} 
        setIsScanning={setIsScanning} 
        setUpdateStatus={setUpdateStatus}
        isLoading={isLoading}
      />
    </div>
  );
};

const ScannerView = ({ handleError, handleScan }) => (
  <div className="relative aspect-square bg-gray-900 rounded-lg overflow-hidden">
    <QrReader
      delay={300}
      onError={handleError}
      onScan={handleScan}
      style={{ width: '100%', height: '100%' }}
      constraints={{
        video: { facingMode: 'environment' }
      }}
    />
    <div className="absolute inset-0 flex items-center justify-center">
      <div className="w-48 h-48 border-2 border-green-500 border-dashed animate-pulse rounded-lg"></div>
    </div>
    <div className="absolute inset-x-0 bottom-4 flex justify-center">
      <div className="bg-black bg-opacity-50 text-white text-center py-2 px-4 rounded-full text-sm">
        Position QR code within the frame
      </div>
    </div>
  </div>
);

const IdleView = () => (
  <div className="flex flex-col items-center justify-center py-8 space-y-4">
    <div className="relative">
      <QrCode size={100} className="text-green-600" />
      <div className="absolute -right-2 -bottom-2 bg-green-100 rounded-full p-1">
        <Camera size={24} className="text-green-600" />
      </div>
    </div>
    <p className="text-gray-600 text-center">Scan QR code to collect waste</p>
  </div>
);

const ScanButton = ({ isScanning, setIsScanning, setUpdateStatus, isLoading }) => (
  <button
    onClick={() => {
      setIsScanning(!isScanning);
      setUpdateStatus(null);
    }}
    disabled={isLoading}
    className={`w-full mt-4 py-3 px-6 rounded-lg text-lg font-semibold transition-all duration-300 flex items-center justify-center space-x-2 ${
      isScanning
        ? 'bg-red-600 hover:bg-red-700 text-white'
        : 'bg-green-600 hover:bg-green-700 text-white'
    } ${isLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
  >
    {isLoading ? (
      <>
        <Loader2 className="animate-spin" size={20} />
        <span>Processing...</span>
      </>
    ) : isScanning ? (
      <>
        <XCircle size={20} />
        <span>Stop Scanning</span>
      </>
    ) : (
      <>
        <Camera size={20} />
        <span>Start Scanning</span>
      </>
    )}
  </button>
);

export default ScanTab;