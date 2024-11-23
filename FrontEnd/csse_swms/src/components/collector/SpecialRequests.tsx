import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { fetchSpecialRequests } from './service/service';

const SpecialRequests = () => {
  const [requests, setRequests] = useState([]);

  // Fetching data from the backend
  // useEffect(() => {
  //   const fetchRequests = async () => {
  //     try {
  //       const token = sessionStorage.getItem('token') // Replace with actual token
  //       const response = await fetch('http://localhost:8081/getAllSpecialWastes', {
  //         headers: {
  //           'Authorization': `Bearer ${token}`,
  //         },
  //       }); // Adjust the path if needed
  //       if (response.ok) {
  //         const data = await response.json();
  //         setRequests(data);
  //       } else {
  //         console.error('Error fetching special waste data');
  //       }
  //     } catch (error) {
  //       console.error('Network error:', error);
  //     }
  //   };

  //   const fitchCors = async () => {
  //     try {
  //       const token = sessionStorage.getItem('token') 
  //       const response = await fetch('http://localhost:8081/test/cors', {
  //         headers: {
  //           'Authorization': `Bearer ${token}`,
  //         },
  //       });
  //       if (response.ok) {
  //         const data = await response.json();
  //         console.log("Below Printing")
  //         console.log(data);
  //       //  setRequests(data);
  //       } else {
  //         console.error('Error fetching special waste data');
  //       }
  //     } catch (error) {
  //       console.error('Network error:', error)
  //     }
  //   }
  




  //   fetchRequests(), fitchCors();
  // }, []);

  





  const fetchCors = async () => {
    try {
      const token = sessionStorage.getItem('token');
      const response = await axios.get('http://localhost:8081/test/cors', {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
  
      if (response.status === 200) {
        const data = response.data;
        console.log("Below Printing");
        console.log(data);
        // setRequests(data); // Uncomment if you are using this state
      } else {
        console.error('Error fetching special waste data');
      }
    } catch (error) {
      console.error('Network error:', error);
    }
  };

  const fetchRequests = async () => {
    // Implement fetchRequests logic here if needed
    try {
            const token = sessionStorage.getItem('token') // Replace with actual token
            // const response = await axios.get('http://localhost:8081/test/getAllSpecialWastes', {
            //   headers: {
            //     'Authorization': `Bearer ${token}`,
            //   },
            // }); // Adjust the path if needed
            
          fetchSpecialRequests().then((response) => {
            if (response.status === 200) {
              const data = response.data;
              setRequests(data);
            } else {
              console.error('Error fetching special waste data');
            }
          }).catch((error) => {
            console.error('Network error:', error);
          });
        }
          catch (error) {
            console.error('Network error:', error);
          }
  };
  
  // Call fetchRequests and fetchCors when the component mounts
  useEffect(() => {
   
  
    fetchRequests();
    fetchCors();
  }, []);











  const updateStatus = async (uuid, newStatus) => {
    try {
      const response = await fetch('http://localhost:8081/updateStatus', {
        method: 'POST', // Assuming it's a POST request, change to PUT if needed
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: uuid,   // Pass the request ID (uuid)
          status: newStatus,  // Pass the new status
        }),
      });

      if (response.ok) {
        console.log('Status updated successfully');
      } else {
        console.error('Failed to update status');
      }
    } catch (error) {
      console.error('Network error:', error);
    }
  };

  // Handle status change (you can extend this with actual backend interaction)
  const handleStatusChange = (id, newStatus) => {
    setRequests(
      requests.map(request =>
        request.uuid === id ? { ...request, status: newStatus } : request
      )
    );

    updateStatus(id, newStatus);
  };

  // Get CSS class for status
  const getStatusColor = status => {
    switch (status) {
      case 'Completed':
        return 'bg-green-100 text-green-800';
      case 'Accepted':
        return 'bg-blue-100 text-blue-800';
      default:
        return 'bg-yellow-100 text-yellow-800';
    }
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Specialll Requests</h1>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <ul className="divide-y divide-gray-200">
          {requests.map(request => (
            <li key={request.id} className="p-4">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between">
                <div className="mb-2 sm:mb-0">
                  <h3 className="text-lg font-medium">{request.typeofwaste}</h3>
                  <p className="text-lg text-black ">Name: {request.name}</p>
                  <p className="text-md text-gray-900">Address: {request.address}</p>
                  <p className="text-md text-gray-900">Number: {request.number}</p>
                  <p className="text-md text-gray-700">City: {request.city}</p>
                  {console.log("status", request.uuid)}
                </div>
                <div className="flex items-center">
                  <span className={`px-2 py-1 text-xs font-semibold rounded-full ${getStatusColor(request.status)}`}>
                    {request.status}
                  </span>
                  <select
                    value={request.status}
                    onChange={(e) => handleStatusChange(request.uuid, e.target.value)}
                    className="ml-2 block w-full sm:w-auto py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500 sm:text-sm"
                  >
                    <option value="Pending">Pending</option>
                    <option value="Accepted">Accepted</option>
                    <option value="Completed">Completed</option>
                  </select>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default SpecialRequests;
