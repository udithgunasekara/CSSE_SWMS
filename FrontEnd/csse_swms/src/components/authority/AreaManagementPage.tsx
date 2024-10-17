import axios from 'axios';
import React, { act, useEffect, useState } from 'react';

interface City {
  cityid: string;
  cityname: string;
  activemodel: string;
}

interface CollectionModel {
  id: string;
  modelname: string;
  fee: string;
}


interface Facility {
  facilityid: string;
  facilityAddress: string;
  latitude: String;
  longitude: String;
}

const AreaManagementPage: React.FC = () => {
  
  const [cities, setCities] = useState<City[]>([]);
  const [collectionModel,setCollectionModel] = useState<CollectionModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [facilities, setFacilities] = useState<Facility[]>([]);

  useEffect(() => {
    const fetchData = async () => { 
      
      try {
        const [citiesResponse, collectionModelResponse,processingfacilityResponse] = await Promise.all([
          axios.get<City[]>('http://localhost:8081/api/city'),
          axios.get<CollectionModel[]>('http://localhost:8081/api/model'),
          axios.get<Facility[]>('http://localhost:8081/api/profacility')
        ]);
        setCities(citiesResponse.data);
        console.log(citiesResponse.data);
        setCollectionModel(collectionModelResponse.data);
        console.log(collectionModelResponse.data);
        setFacilities(processingfacilityResponse.data);
        console.log(processingfacilityResponse.data);
        setLoading(false);
      } catch (error) {
        setError('Error fetching data');
        setLoading(false);
      }
    }
    fetchData();
},[]);

  const handleModelChange = async (cityId: string, newModel: string) => {
    try {
      await axios.patch(`http://localhost:8081/api/city/${cityId}?activeModel=${newModel}`);
      setCities(
        prevCities => prevCities.map(
          city => city.cityid === cityId ? {...city, activemodel: newModel} : city
        )
      );
    } catch (err) {
      setError('Error updating city');
    }
  };

  const handleDeleteCity = async (cityId: string) => {
    try {
      await axios.delete(`http://localhost:8081/api/city/${cityId}`);
      setCities(cities.filter(city => city.cityid !== cityId));
    } catch (err) {
      setError('Error deleting city');
    }
  };

  const handleDeleteFacility = async (facilityid: string) => {
    try {
      await axios.delete(`http://localhost:8081/api/profacility/${facilityid}`);
      setFacilities(facilities.filter(facility => facility.facilityid !== facilityid));
    } catch (err) {
      setError('Error deleting city');
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;


  return (
    <div className="p-4">
      <h1 className="text-2xl md:text-3xl font-bold mb-6">Area Management</h1>

      <div className="space-y-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">Cities</h2>
            <button className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 w-full sm:w-auto">
              Add City
            </button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    City Name
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Model
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {cities.map((city) => (
                  <tr key={city.cityid}>
                    <td className="px-6 py-4 whitespace-nowrap">{city.cityname}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <select className="border rounded p-1" 
                        value={city.activemodel} 
                        onChange={(e) => {handleModelChange(city.cityid,e.target.value)
                          console.log(e.target.value)}
                        }
                      >
                        {collectionModel.map(model => (
                          <option key={model.id} value={model.modelname}>
                            {model.modelname}
                          </option>
                          )
                        )}                        
                      </select>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <button className="text-red-500 hover:text-red-700"
                        onClick={() => handleDeleteCity(city.cityid)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">
              Processing Facilities
            </h2>
            <button className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600 w-full sm:w-auto">
              Add Facility
            </button>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full min-w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    FacilityID
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Address
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Location
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {facilities.map((facility) => (
                  <tr key={facility.facilityid}>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.facilityid}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.facilityAddress}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      {facility.latitude}, {facility.longitude}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <button className="text-blue-500 hover:text-blue-700 mr-2">
                        Edit
                      </button>
                      <button className="text-red-500 hover:text-red-700"
                        onClick={() => handleDeleteFacility(facility.facilityid)}
                      >                        
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AreaManagementPage;
