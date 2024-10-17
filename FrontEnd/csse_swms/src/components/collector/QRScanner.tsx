import React, { useState, useCallback } from 'react';
import QrReader from 'react-qr-scanner';
import { QrCode, User, Trash2, XCircle, CheckCircle, Loader2, Camera, History, HelpCircle } from 'lucide-react';

const API_BASE_URL = 'http://localhost:8081/api/cp';

const OptimizedQRScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [isScanning, setIsScanning] = useState(false);
  const [updateStatus, setUpdateStatus] = useState(null);
  const [scanHistory, setScanHistory] = useState([]);
  const [selectedTab, setSelectedTab] = useState('scan');
  const [isLoading, setIsLoading] = useState(false);

  const handleWasteCollection = async (trashbinId, userId) => {
    setIsLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/update/${trashbinId}?userid=${userId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const result = await response.text();
      
      if (response.ok) {
        setUpdateStatus({ success: true, message: result });
        setScanHistory(prev => [{
          userID: userId,
          trashbinId: trashbinId,
          timestamp: new Date().toLocaleString(),
          status: 'Success'
        }, ...prev.slice(0, 4)]);
      } else {
        const errorMessage = result || 'An error occurred';
        setUpdateStatus({ 
          success: false, 
          message: errorMessage
        });
        
        setScanHistory(prev => [{
          userID: userId,
          trashbinId: trashbinId,
          timestamp: new Date().toLocaleString(),
          status: 'Failed'
        }, ...prev.slice(0, 4)]);
      }

    } catch (error) {
      setUpdateStatus({ 
        success: false, 
        message: 'Network error: Could not connect to the server'
      });
      
      setScanHistory(prev => [{
        userID: userId,
        trashbinId: trashbinId,
        timestamp: new Date().toLocaleString(),
        status: 'Failed'
      }, ...prev.slice(0, 4)]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleScan = useCallback((data) => {
    if (data) {
      try {
        const parsedData = JSON.parse(data.text);
        setScannedData(parsedData);
        setIsScanning(false);
        
        if (!parsedData.trashbinId || !parsedData.userid) {
          throw new Error('Invalid QR code: Missing required fields');
        }
        
        handleWasteCollection(parsedData.trashbinId, parsedData.userid);
      } catch (error) {
        setUpdateStatus({ 
          success: false, 
          message: 'Invalid QR code format. Expected format: {"trashbinId": "...", "userId": "..."}' 
        });
        setIsScanning(false);
      }
    }
  }, []);

  const handleError = useCallback((err) => {
    setUpdateStatus({ 
      success: false, 
      message: 'Error accessing camera. Please check camera permissions.' 
    });
    setIsScanning(false);
  }, []);

  const resetStatus = () => {
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
              <div>{scanHistory.length}</div>
            </div>
            <div className="text-center">
              <div className="font-bold">Success Rate</div>
              <div>
                {scanHistory.length > 0 
                  ? `${((scanHistory.filter(scan => scan.status === 'Success').length / scanHistory.length) * 100).toFixed(0)}%`
                  : '0%'}
              </div>
            </div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="bg-white border-b flex justify-around p-2">
          <button
            onClick={() => {
              setSelectedTab('scan');
              resetStatus();
            }}
            className={`flex items-center px-4 py-2 rounded-lg ${
              selectedTab === 'scan' ? 'bg-green-100 text-green-700' : 'text-gray-600'
            }`}
          >
            <Camera size={18} className="mr-2" />
            Scan
          </button>
          <button
            onClick={() => setSelectedTab('history')}
            className={`flex items-center px-4 py-2 rounded-lg ${
              selectedTab === 'history' ? 'bg-green-100 text-green-700' : 'text-gray-600'
            }`}
          >
            <History size={18} className="mr-2" />
            History
          </button>
          <button
            onClick={() => setSelectedTab('help')}
            className={`flex items-center px-4 py-2 rounded-lg ${
              selectedTab === 'help' ? 'bg-green-100 text-green-700' : 'text-gray-600'
            }`}
          >
            <HelpCircle size={18} className="mr-2" />
            Help
          </button>
        </div>

        {/* Main Content */}
        <div className="bg-white shadow-lg rounded-b-lg overflow-hidden">
          {selectedTab === 'scan' && (
            <div className="p-4">
              {isScanning ? (
                <div className="relative aspect-square bg-gray-900 rounded-lg overflow-hidden">
                  <QrReader
                    delay={300}
                    onError={handleError}
                    onScan={handleScan}
                    style={{ width: '100%' }}
                    constraints={{
                      video: { facingMode: 'environment' }
                    }}
                  />
                  <div className="absolute inset-0 flex items-center justify-center">
                    <div className="w-48 h-48 border-2 border-green-500 border-dashed animate-pulse rounded-lg"></div>
                  </div>
                  <div className="absolute bottom-0 left-0 right-0 bg-black bg-opacity-50 text-white text-center py-2 text-sm">
                    Position QR code within the frame
                  </div>
                </div>
              ) : (
                <div className="flex flex-col items-center justify-center py-8 space-y-4">
                  <div className="relative">
                    <QrCode size={100} className="text-green-600" />
                    <div className="absolute -right-2 -bottom-2 bg-green-100 rounded-full p-1">
                      <Camera size={24} className="text-green-600" />
                    </div>
                  </div>
                  <p className="text-gray-600 text-center">Scan QR code to collect waste</p>
                </div>
              )}

              <button
                onClick={() => {
                  setIsScanning(!isScanning);
                  resetStatus();
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
            </div>
          )}

          {selectedTab === 'history' && (
            <div className="p-4">
              <h2 className="text-lg font-semibold mb-4">Recent Collections</h2>
              {scanHistory.length > 0 ? (
                <div className="space-y-3">
                  {scanHistory.map((scan, index) => (
                    <div 
                      key={index} 
                      className={`p-3 rounded-lg ${
                        scan.status === 'Success' ? 'bg-green-50' : 'bg-red-50'
                      }`}
                    >
                      <div className="flex justify-between items-start">
                        <div>
                          <div className="flex items-center text-sm text-gray-600">
                            <User size={16} className="mr-2" />
                            User ID: {scan.userID}
                          </div>
                          <div className="flex items-center text-sm text-gray-600 mt-1">
                            <Trash2 size={16} className="mr-2" />
                            Bin ID: {scan.trashbinId}
                          </div>
                        </div>
                        <div className="text-right">
                          <span className="text-xs text-gray-500 block">{scan.timestamp}</span>
                          <span className={`text-xs font-medium ${
                            scan.status === 'Success' ? 'text-green-600' : 'text-red-600'
                          }`}>
                            {scan.status}
                          </span>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center text-gray-500 py-8">
                  No collection history available
                </div>
              )}
            </div>
          )}

          {selectedTab === 'help' && (
            <div className="p-4">
              <h2 className="text-lg font-semibold mb-4">How to Use</h2>
              <div className="space-y-3">
                <div className="flex items-start space-x-3">
                  <div className="bg-green-100 p-2 rounded-full">
                    <Camera size={20} className="text-green-600" />
                  </div>
                  <div>
                    <h3 className="font-medium">Start Scanning</h3>
                    <p className="text-sm text-gray-600">Click the scan button to activate your camera</p>
                  </div>
                </div>
                <div className="flex items-start space-x-3">
                  <div className="bg-green-100 p-2 rounded-full">
                    <QrCode size={20} className="text-green-600" />
                  </div>
                  <div>
                    <h3 className="font-medium">Position QR Code</h3>
                    <p className="text-sm text-gray-600">Align the QR code within the scanning frame</p>
                  </div>
                </div>
                <div className="flex items-start space-x-3">
                  <div className="bg-green-100 p-2 rounded-full">
                    <CheckCircle size={20} className="text-green-600" />
                  </div>
                  <div>
                    <h3 className="font-medium">Confirm Collection</h3>
                    <p className="text-sm text-gray-600">Wait for the success message to confirm waste collection</p>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Status Messages */}
          {updateStatus && (
            <div 
              className={`m-4 p-4 rounded-lg flex items-start space-x-2 ${
                updateStatus.success 
                  ? 'bg-green-50 text-green-800 border border-green-200' 
                  : 'bg-red-50 text-red-800 border border-red-200'
              }`}
            >
              {updateStatus.success ? (
                <CheckCircle className="h-5 w-5 flex-shrink-0" />
              ) : (
                <XCircle className="h-5 w-5 flex-shrink-0" />
              )}
              <div>
                <div className="font-medium">
                  {updateStatus.success ? 'Success' : 'Error'}
                </div>
                <div className="text-sm mt-1">
                  {updateStatus.message}
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default OptimizedQRScanner;