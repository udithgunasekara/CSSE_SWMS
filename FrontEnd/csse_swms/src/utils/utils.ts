export const getTodayCollections = (scanHistory) => {
    const today = new Date().toISOString().split('T')[0];
    return scanHistory.filter(item => item.date === today).length;
  };
  
  export const getTotalWeight = (scanHistory) => {
    return scanHistory.reduce((total, item) => total + item.weight, 0);
  };
  
  export const parseQRCode = (data) => {
    try {
      const parsedData = JSON.parse(data.text);
      if (!parsedData.trashbinId || !parsedData.userid) {
        throw new Error('Invalid QR code: Missing required fields');
      }
      return parsedData;
    } catch (error) {
      throw new Error('Invalid QR code format. Expected format: {"trashbinId": "...", "userId": "..."}');
    }
  };