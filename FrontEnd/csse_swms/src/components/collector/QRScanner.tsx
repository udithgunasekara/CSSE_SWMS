import React, { useState, useCallback, useEffect } from 'react';
import QrReader from 'react-qr-scanner';
import { QrCode, User, Trash2, XCircle, CheckCircle, Loader2, Camera, History, HelpCircle, Clock, Calendar, Weight } from 'lucide-react';

const API_BASE_URL = 'http://localhost:8081/api/cp';
const HISTORY_API_URL = 'http://localhost:8081/api/history/user/985d0557';

const OptimizedQRScanner = () => {
  const [scannedData, setScannedData] = useState(null);
  const [isScanning, setIsScanning] = useState(false);
  const [updateStatus, setUpdateStatus] = useState(null);
  const [scanHistory, setScanHistory] = useState([]);
  const [selectedTab, setSelectedTab] = useState('scan');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    fetchUserHistory();
  }, []);

  const fetchUserHistory = async () => {
    try {
      const response = await fetch(HISTORY_API_URL);
      if (response.ok) {
        const data = await response.json();
        setScanHistory(data);
      } else {
        console.error('Failed to fetch user history');
      }
    } catch (error) {
      console.error('Error fetching user history:', error);
    }
  };

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
        await fetchUserHistory(); // Refresh history after successful collection
      } else {
        const errorMessage = result || 'An error occurred';
        setUpdateStatus({ 
          success: false, 
          message: errorMessage
        });
      }

    } catch (error) {
      setUpdateStatus({ 
        success: false, 
        message: 'Network error: Could not connect to the server'
      });
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

  const getTodayCollections = () => {
    const today = new Date().toISOString().split('T')[0];
    return scanHistory.filter(item => item.date === today).length;
  };

  const getTotalWeight = () => {
    return scanHistory.reduce((total, item) => total + item.weight, 0);
  };

  const handleTabChange = (tab) => {
    setSelectedTab(tab);
    if (tab === 'history') {
      fetchUserHistory(); // Fetch history when the history tab is clicked
    }
    resetStatus();
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
              <div>{getTodayCollections()}</div>
            </div>
            <div className="text-center">
              <div className="font-bold">Total Weight</div>
              <div>{getTotalWeight()} kg</div>
            </div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="bg-white border-b flex justify-around p-2">
          <button
            onClick={() => handleTabChange('scan')}
            className={`flex items-center px-4 py-2 rounded-lg ${
              selectedTab === 'scan' ? 'bg-green-100 text-green-700' : 'text-gray-600'
            }`}
          >
            <Camera size={18} className="mr-2" />
            Scan
          </button>
          <button
            onClick={() => handleTabChange('history')}
            className={`flex items-center px-4 py-2 rounded-lg ${
              selectedTab === 'history' ? 'bg-green-100 text-green-700' : 'text-gray-600'
            }`}
          >
            <History size={18} className="mr-2" />
            History
          </button>
          <button
            onClick={() => handleTabChange('help')}
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
                    style={{ width: '100%', height: '100%' }}
                    constraints={{
                      video: { facingMode: 'environment' }
                    }}
                  />
                  <div className="absolute inset-0 flex items-center justify-center">
                    <div className="w-48 h-48 border-2 border-green-500 border-dashed animate-pulse rounded-lg"></div>
                  </div>
                  {/* Updated instructions overlay */}
                  <div className="absolute inset-x-0 bottom-4 flex justify-center">
                    <div className="bg-black bg-opacity-50 text-white text-center py-2 px-4 rounded-full text-sm">
                      Position QR code within the frame
                    </div>
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
              <h2 className="text-lg font-semibold mb-4">Collection History</h2>
              {scanHistory.length > 0 ? (
                <div className="space-y-3">
                  {scanHistory.map((scan) => (
                    <div 
                      key={scan.historyID} 
                      className="p-3 rounded-lg bg-green-50"
                    >
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