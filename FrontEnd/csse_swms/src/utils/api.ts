const API_BASE_URL = 'http://localhost:8081/api';
const userId = '985d0557';

export const fetchUserHistory = async (userId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/history/user/985d0557`);
    if (!response.ok) {
      throw new Error('Failed to fetch user history');
    }
    return await response.json();
  } catch (error) {
    console.error('Error fetching user history:', error);
    throw error;
  }
};

export const handleWasteCollection = async (trashbinId, userId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/cp/update/${trashbinId}?userid=${userId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    const result = await response.text();
    
    if (!response.ok) {
      throw new Error(result || 'An error occurred');
    }

    return result;
  } catch (error) {
    console.error('Error handling waste collection:', error);
    throw error;
  }
};