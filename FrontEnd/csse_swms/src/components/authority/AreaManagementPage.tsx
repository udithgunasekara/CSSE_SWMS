import axios from 'axios';
import { circIn } from 'framer-motion';
import { PlusCircle, X } from 'lucide-react';
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

  const [showAddCityForm, setShowAddCityForm] = useState<boolean>(false);
  const [showFacilityForm, setShowFacilityForm] = useState<boolean>(false);
  const [newCity, setNewCity] = useState({cityname: '' , activemodel: ''});
  // const [newFacility, setNewFacility] = useState({facilityAddress: '', latitude: '', longitude: ''});
  const [facilityFormData, setFacilityFormData] = useState({facilityid: '',facilityAddress: '', latitude: '', longitude: ''});
  const [isEditing, setIsEditing] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => { 
      
      try {
        const [citiesResponse, collectionModelResponse,processingfacilityResponse] = await Promise.all([
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

  const handleEditFacility = (facility: Facility) => {
    setFacilityFormData({
      facilityid: facility.facilityid,
      facilityAddress: facility.facilityAddress,
      latitude: facility.latitude.toString(),
      longitude: facility.longitude.toString()
    });
    setIsEditing(true);
    setShowFacilityForm(true);
  };

  const handleAddCity = async (e:React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/api/city', newCity);
      const addedCity = response.data;

      if (!addedCity.cityid || !addedCity.cityname || !addedCity.activemodel) {
        throw new Error('Invalid city data received from server');
      }

      setCities(prevCities => [...prevCities, addedCity]);
      setNewCity({ cityname: '', activemodel: '' });
      setShowAddCityForm(false);
      setError(null);
    } catch (err) {
      console.error('Error adding city:', err); // More detailed error logging
    setError('Error adding city');
    }
  }

  const handleAddFacility = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (isEditing) {
        // Update existing facility
        const response = await axios.put(
          `http://localhost:8081/api/profacility/${facilityFormData.facilityid}`, 
          {
            facilityAddress: facilityFormData.facilityAddress,
            latitude: facilityFormData.latitude,
            longitude: facilityFormData.longitude
          }
        );

        setFacilities(prevFacilities => 
          prevFacilities.map(facility => 
            facility.facilityid === facilityFormData.facilityid 
              ? { ...facility, ...facilityFormData }
              : facility
          )
        );
      } else {
        // Add new facility
        const response = await axios.post('http://localhost:8081/api/profacility', facilityFormData);
        const addedFacility = response.data;

        if (!addedFacility.facilityAddress || !addedFacility.latitude || !addedFacility.longitude || !addedFacility.facilityid) {
          throw new Error('Invalid facility data received from the server');
        }

        setFacilities(prevFacilities => [...prevFacilities, addedFacility]);
      }

      // Reset form
      setFacilityFormData({
        facilityid: '',
        facilityAddress: '',
        latitude: '',
        longitude: ''
      });
      setShowFacilityForm(false);
      setIsEditing(false);
      setError(null);
    } catch (err) {
      setError(isEditing ? 'Error updating facility' : 'Error adding facility');
    }
  };

  const handleCancelFacilityForm = () => {
    setShowFacilityForm(false);
    setIsEditing(false);
    setFacilityFormData({
      facilityid: '',
      facilityAddress: '',
      latitude: '',
      longitude: ''
    });
  };

  useEffect(() => {
    console.log("Cities updated " , cities);
  },[cities]);

  useEffect(() => {
    console.log("Facilities updated " , facilities);
  },[facilities]);

  if (loading) return <div className="flex justify-center items-center h-screen">Loading...</div>;
  if (error) return <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">{error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl md:text-3xl font-bold mb-6">Area Management</h1>

      <div className="space-y-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">Cities</h2>
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
          <div className="relative">
            <div className="overflow-x-auto">
              <div className="max-h-[350px] overflow-y-auto">
                <table className="w-full min-w-full">
                  <thead className="bg-gray-50 sticky top-0 z-10">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
                        City Name
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
                        Model
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
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
          </div>
        </div>

        <div className="bg-white p-4 rounded-lg shadow">
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
            <h2 className="text-xl font-semibold mb-2 sm:mb-0">
              Processing Facilities
            </h2>
            {!showFacilityForm && (
            <button 
              className="flex items-center bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md transition duration-300 ease-in-out"
              onClick={() => setShowFacilityForm(true)}
            >
              <PlusCircle size={18} className="mr-2" />
              Add Facility
            </button>
          )}
          </div>
          {showFacilityForm && (
          <form onSubmit={handleAddFacility} className="p-6 bg-gray-50 border-b border-gray-200">
            <div className="flex flex-col space-y-4">
              <input
                type="text"
                placeholder="Facility Address"
                value={facilityFormData.facilityAddress}
                onChange={(e) => setFacilityFormData({...facilityFormData, facilityAddress: e.target.value})}
                className="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              />
              <div className="flex space-x-4">
                <input
                  type="text"
                  placeholder="Latitude"
                  value={facilityFormData.latitude}
                  onChange={(e) => setFacilityFormData({...facilityFormData, latitude: e.target.value})}
                  className="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
                <input
                  type="text"
                  placeholder="Longitude"
                  value={facilityFormData.longitude}
                  onChange={(e) => setFacilityFormData({...facilityFormData, longitude: e.target.value})}
                  className="flex-1 border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div className="flex space-x-4">
                <button 
                  type="submit" 
                  className="flex-1 bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md transition duration-300 ease-in-out"
                >
                  {isEditing ? 'Update' : 'Add'} Facility
                </button>
                <button 
                  type="button"
                  onClick={handleCancelFacilityForm}
                  className="flex-1 bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-md transition duration-300 ease-in-out"
                >
                  Cancel
                </button>
              </div>
            </div>
          </form>
        )}
          <div className="relative">
            <div className="overflow-x-auto">
              <div className="max-h-[400px] overflow-y-auto">
                <table className="w-full min-w-full">
                  <thead className="bg-gray-50 sticky top-0 z-10">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
                        FacilityID
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
                        Address
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
                        Location
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider bg-gray-50">
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
                    <button 
                      className="text-blue-500 hover:text-blue-700 mr-4"
                      onClick={() => handleEditFacility(facility)}
                    >
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
      </div>
    </div>
  );
};

export default AreaManagementPage;
