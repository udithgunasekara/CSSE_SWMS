import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { PlusCircle, X, Edit2, Trash2 } from 'lucide-react';

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
  latitude: string;
  longitude: string;
}

const AreaManagementPage: React.FC = () => {
  const [cities, setCities] = useState<City[]>([]);
  const [collectionModel, setCollectionModel] = useState<CollectionModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [facilities, setFacilities] = useState<Facility[]>([]);
  
  const [showAddCityForm, setShowAddCityForm] = useState(false);
  const [showAddFacilityForm, setShowAddFacilityForm] = useState(false);
  const [newCity, setNewCity] = useState({ cityname: '', activemodel: '' });
  const [newFacility, setNewFacility] = useState({ facilityAddress: '', latitude: '', longitude: '' });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [citiesResponse, collectionModelResponse, processingfacilityResponse] = await Promise.all([
          axios.get<City[]>('http://localhost:8081/api/city'),
          axios.get<CollectionModel[]>('http://localhost:8081/api/model'),
          axios.get<Facility[]>('http://localhost:8081/api/profacility')
        ]);
        setCities(citiesResponse.data);
        setCollectionModel(collectionModelResponse.data);
        setFacilities(processingfacilityResponse.data);
        setLoading(false);
      } catch (error) {
        setError('Error fetching data');
        setLoading(false);
      }
    }
    fetchData();
  }, []);

  const handleModelChange = async (cityId: string, newModel: string) => {
    try {
      await axios.patch(`http://localhost:8081/api/city/${cityId}?activeModel=${newModel}`);
      setCities(prevCities => prevCities.map(city => city.cityid === cityId ? {...city, activemodel: newModel} : city));
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
      setError('Error deleting facility');
    }
  };

  const handleAddCity = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/api/city', newCity);
      setCities([...cities, response.data]);
      setNewCity({ cityname: '', activemodel: '' });
      setShowAddCityForm(false);
    } catch (err) {
      setError('Error adding city');
    }
  };

  const handleAddFacility = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/api/profacility', newFacility);
      setFacilities([...facilities, response.data]);
      setNewFacility({ facilityAddress: '', latitude: '', longitude: '' });
      setShowAddFacilityForm(false);
    } catch (err) {
      setError('Error adding facility');
    }
  };

  if (loading) return <div className="flex justify-center items-center h-screen">Loading...</div>;
  if (error) return <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">{error}</div>;

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Area Management</h1>

      <div className="space-y-8">
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <div className="flex justify-between items-center bg-gray-50 px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-800">Cities</h2>
            <button 
              className={`flex items-center ${showAddCityForm ? 'bg-red-500 hover:bg-red-600' : 'bg-blue-500 hover:bg-blue-600'} text-white px-4 py-2 rounded-md transition duration-300 ease-in-out`}
              onClick={() => setShowAddCityForm(!showAddCityForm)}
            >
              {showAddCityForm ? (
                <>
                  <X size={18} className="mr-2" />
                  Cancel
                </>
              ) : (
                <>
                  <PlusCircle size={18} className="mr-2" />
                  Add City
                </>
              )}
            </button>
          </div>
          {showAddCityForm && (
            <form onSubmit={handleAddCity} className="p-6 bg-gray-50 border-b border-gray-200">
              <div className="flex space-x-4">
                <input
                  type="text"
                  placeholder="City Name"
                  value={newCity.cityname}
                  onChange={(e) => setNewCity({...newCity, cityname: e.target.value})}
                  className="flex-grow border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
                <select
                  value={newCity.activemodel}
                  onChange={(e) => setNewCity({...newCity, activemodel: e.target.value})}
                  className="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  <option value="">Select Model</option>
                  {collectionModel.map(model => (
                    <option key={model.id} value={model.modelname}>
                      {model.modelname}
                    </option>
                  ))}
                </select>
                <button type="submit" className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md transition duration-300 ease-in-out">
                  Add
                </button>
              </div>
            </form>
          )}
          <div className="overflow-x-auto">
            <table className="w-full">
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
                  <tr key={city.cityid} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">{city.cityname}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <select 
                        className="border border-gray-300 rounded-md px-2 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                        value={city.activemodel} 
                        onChange={(e) => handleModelChange(city.cityid, e.target.value)}
                      >
                        {collectionModel.map(model => (
                          <option key={model.id} value={model.modelname}>
                            {model.modelname}
                          </option>
                        ))}                        
                      </select>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <button 
                        className="text-red-600 hover:text-red-900"
                        onClick={() => handleDeleteCity(city.cityid)}
                      >
                        <Trash2 size={18} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <div className="flex justify-between items-center bg-gray-50 px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-800">Processing Facilities</h2>
            <button 
              className={`flex items-center ${showAddFacilityForm ? 'bg-red-500 hover:bg-red-600' : 'bg-blue-500 hover:bg-blue-600'} text-white px-4 py-2 rounded-md transition duration-300 ease-in-out`}
              onClick={() => setShowAddFacilityForm(!showAddFacilityForm)}
            >
              {showAddFacilityForm ? (
                <>
                  <X size={18} className="mr-2" />
                  Cancel
                </>
              ) : (
                <>
                  <PlusCircle size={18} className="mr-2" />
                  Add Facility
                </>
              )}
            </button>
          </div>
          {showAddFacilityForm && (
            <form onSubmit={handleAddFacility} className="p-6 bg-gray-50 border-b border-gray-200">
              <div className="flex space-x-4">
                <input
                  type="text"
                  placeholder="Facility Address"
                  value={newFacility.facilityAddress}
                  onChange={(e) => setNewFacility({...newFacility, facilityAddress: e.target.value})}
                  className="flex-grow border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
                <input
                  type="text"
                  placeholder="Latitude"
                  value={newFacility.latitude}
                  onChange={(e) => setNewFacility({...newFacility, latitude: e.target.value})}
                  className="w-32 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
                <input
                  type="text"
                  placeholder="Longitude"
                  value={newFacility.longitude}
                  onChange={(e) => setNewFacility({...newFacility, longitude: e.target.value})}
                  className="w-32 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
                <button type="submit" className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md transition duration-300 ease-in-out">
                  Add
                </button>
              </div>
            </form>
          )}
          <div className="overflow-x-auto">
            <table className="w-full">
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
                  <tr key={facility.facilityid} className="hover:bg-gray-50">
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
                      <button className="text-blue-600 hover:text-blue-900 mr-3">
                        <Edit2 size={18} />
                      </button>
                      <button 
                        className="text-red-600 hover:text-red-900"
                        onClick={() => handleDeleteFacility(facility.facilityid)}
                      >
                        <Trash2 size={18} />
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