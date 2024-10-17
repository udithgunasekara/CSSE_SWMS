import React, { useState, useRef, useEffect } from 'react';
import { GoogleMap, LoadScript, DirectionsRenderer } from '@react-google-maps/api';
import { getToCollectBins } from '../services/map';  // Import the service method

const containerStyle = {
  width: '100%',
  height: '100%',
};

const center = {
  lat: 6.9271,  // Colombo coordinates as example
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

const startPoint = { lat: 6.9269, lng: 79.8700 };  // Example starting point

const RouteMap = () => {
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [routeDetails, setRouteDetails] = useState({ distance: 0, time: 0 });
  const [trashbins, setTrashBins] = useState<TrashBin[]>([]);  // State for dynamic bins
  const mapRef = useRef<any>(null);  // Ref to persist map state

  // Fetch trash bin data on mount
  useEffect(() => {
    const fetchBins = async () => {
      try {
        const binsData = await getToCollectBins();  // No need to access `response.data` anymore
        console.log("Fetched bins:", binsData);  // Debugging log to see the fetched data
        setTrashBins(binsData);  // Update state with the fetched bins
        if (binsData.length > 0) {
          calculateRoute(binsData);  // Calculate the route based on the fetched bins
        }
      } catch (error) {
        console.error('Error fetching bins:', error);
      }
    };
  
    fetchBins();
  }, []);
  

  // Function to calculate the route using fetched trash bins
  const calculateRoute = (binsData: TrashBin[]) => {
    const directionsService = new google.maps.DirectionsService();

    const waypoints = binsData.slice(0, binsData.length - 1).map((bin) => ({
      location: { lat: parseFloat(bin.latitude), lng: parseFloat(bin.longitude) },
    }));

    directionsService.route(
      {
        origin: startPoint,
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
            distance: (route.distance / 1000).toFixed(2),  // Convert meters to km
            time: Math.round(route.time / 60),  // Convert seconds to minutes
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
        <LoadScript googleMapsApiKey="AIzaSyCtV803a3BAeHMRNxe0QVsQxC83ZGHO16k">
          {trashbins.length > 0 && directionsResponse && (
            <GoogleMap
              mapContainerStyle={containerStyle}
              center={center}
              zoom={13}
              ref={mapRef}  // Persist map state
            >
              {directionsResponse && <DirectionsRenderer directions={directionsResponse} />}
            </GoogleMap>
          )}
        </LoadScript>
      </div>
      <div className="mt-4 bg-white shadow rounded-lg p-4">
        <h2 className="text-xl font-semibold mb-2">Route Details:</h2>
        <ul className="list-disc list-inside text-sm">
          <li>Total distance: {routeDetails.distance} km</li>
          <li>Estimated time: {routeDetails.time} minutes</li>
          <li>Number of bins: {trashbins.length}</li>
          <li>Areas covered: {/* Add areas dynamically based on bin data */}</li>
        </ul>
      </div>
    </div>
  );
};

export default RouteMap;