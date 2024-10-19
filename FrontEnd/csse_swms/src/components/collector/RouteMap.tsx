import React, { useState, useRef, useEffect } from 'react';
import { GoogleMap, DirectionsRenderer } from '@react-google-maps/api';
import { getToCollectBins, getfacilitybyId } from '../services/map'; // Import getFacilityById method

const containerStyle = {
  width: '100%',
  height: '100%',
};

const center = {
  lat: 6.9271,  
  lng: 79.8612,
};

interface TrashBin {
  trashbinId: string;
  trashbinType: string;
  latitude: string;
  longitude: string;
  wasteLevel: number;
  full: boolean;
  collected: boolean;
  assigned: boolean;
}

interface Facility {
  facilityid: string;
  facilityAddress: string;
  latitude: string;
  longitude: string;
}

const RouteMap = () => {
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [routeDetails, setRouteDetails] = useState({ distance: 0, time: 0 });
  const [trashbins, setTrashBins] = useState<TrashBin[]>([]);
  const [facility, setFacility] = useState<Facility | null>(null); // Use state for facility
  const mapRef = useRef<any>(null);
  const prevTrashBinsRef = useRef<TrashBin[]>([]); // Ref to store previous trash bin data

  // Utility function to compare if two arrays of trashbins are the same
  const arraysEqual = (arr1: TrashBin[], arr2: TrashBin[]) => {
    if (arr1.length !== arr2.length) return false;
    return arr1.every((bin, index) =>
      bin.trashbinId === arr2[index].trashbinId &&
      bin.latitude === arr2[index].latitude &&
      bin.longitude === arr2[index].longitude &&
      bin.full === arr2[index].full &&
      bin.collected === arr2[index].collected &&
      bin.assigned === arr2[index].assigned
    );
  };

  // Fetch trash bin data and update the map if the data has changed
  const fetchBinsAndUpdateRoute = async () => {
    try {
      const binsData = await getToCollectBins('39d562b7');
      console.log("Fetched bins:", binsData);

      // Only update if the bins data has changed
      if (!arraysEqual(binsData, prevTrashBinsRef.current)) {
        console.log("Bins data changed, updating map...");
        prevTrashBinsRef.current = binsData; // Store the new data as previous data
        setTrashBins(binsData);
        if (binsData.length > 0 && facility) {
          calculateRoute(binsData); // Recalculate route only when data changes
        }
      } else {
        console.log("Bins data is the same, no update needed.");
      }
    } catch (error) {
      console.error('Error fetching bins:', error);
    }
  };

  // Fetch the facility details by ID
  const fetchFacility = async () => {
    try {
      const facilityData = await getfacilitybyId('39d562b7'); // Replace with the actual ID
      setFacility(facilityData);
      console.log("Fetched facility:", facilityData);
    } catch (error) {
      console.error('Error fetching facility:', error);
    }
  };

  // Fetch facility and bins initially and set up polling for updates
  useEffect(() => {
    fetchFacility(); // Fetch the facility first
  }, []);

  useEffect(() => {
    if (facility) {
      fetchBinsAndUpdateRoute(); // Initial fetch after facility is set
      const intervalId = setInterval(fetchBinsAndUpdateRoute, 30000); // Poll every 30 seconds

      return () => clearInterval(intervalId); // Cleanup interval on unmount
    }
  }, [facility]); // Only run when facility is available

  // Function to calculate the route using fetched trash bins
  const calculateRoute = (binsData: TrashBin[]) => {
    const directionsService = new google.maps.DirectionsService();

    const waypoints = binsData.slice(0, binsData.length - 1).map((bin) => ({
      location: { lat: parseFloat(bin.latitude), lng: parseFloat(bin.longitude) },
    }));

    directionsService.route(
      {
        origin: {
          lat: parseFloat(facility!.latitude), // Use facility's latitude
          lng: parseFloat(facility!.longitude), // Use facility's longitude
        },
        destination: {
          lat: parseFloat(binsData[binsData.length - 1].latitude),
          lng: parseFloat(binsData[binsData.length - 1].longitude),
        },
        travelMode: google.maps.TravelMode.DRIVING,
        waypoints,
        optimizeWaypoints: true,
      },
      (result, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
          setDirectionsResponse(result);
          const route = result.routes[0].legs.reduce(
            (acc, leg) => {
              acc.distance += leg.distance.value;
              acc.time += leg.duration.value;
              return acc;
            },
            { distance: 0, time: 0 }
          );
          setRouteDetails({
            distance: (route.distance / 1000).toFixed(2), // Convert meters to km
            time: Math.round(route.time / 60), // Convert seconds to minutes
          });
        } else {
          console.error('Error fetching directions:', result);
        }
      }
    );
  };

  return (
    <div className="flex flex-col h-full">
      <h1 className="text-2xl font-bold mb-4">Route Map</h1>
      <div className="flex-1 bg-white shadow rounded-lg overflow-hidden">
        {trashbins.length > 0 && directionsResponse && (
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={center}
            zoom={13}
            ref={mapRef}
          >
            {directionsResponse && <DirectionsRenderer directions={directionsResponse} />}
          </GoogleMap>
        )}
      </div>
      <div className="mt-4 bg-white shadow rounded-lg p-4">
        <h2 className="text-xl font-semibold mb-2">Route Details:</h2>
        <ul className="list-disc list-inside text-sm">
          <li>Total distance: {routeDetails.distance} km</li>
          <li>Estimated time: {routeDetails.time} minutes</li>
          <li>Number of bins: {trashbins.length}</li>
        </ul>
      </div>
    </div>
  );
};

export default RouteMap;