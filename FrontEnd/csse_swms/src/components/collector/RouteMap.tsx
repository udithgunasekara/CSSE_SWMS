import React, { useState, useRef } from 'react';
import { GoogleMap, LoadScript, DirectionsRenderer } from '@react-google-maps/api';

const containerStyle = {
  width: '100%',
  height: '100%',
};

const center = {
  lat: 6.9271,  // Colombo coordinates as example
  lng: 79.8612,
};

// Bins are the end points (waypoints)
const bins = [
  { position: { lat: 6.9271, lng: 79.8612 }, name: "Bin 1 (Fort)" },
  { position: { lat: 6.9286, lng: 79.8473 }, name: "Bin 2 (Colombo 02)" },
  { position: { lat: 6.9309, lng: 79.8428 }, name: "Bin 3 (Colombo 03)" },
];

// Starting point for the route
const startPoint = { lat: 6.9269, lng: 79.8700 }; // Example starting point

const RouteMap = () => {
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [routeDetails, setRouteDetails] = useState({ distance: 0, time: 0 });

  const mapRef = useRef(null);

  const calculateRoute = () => {
    const directionsService = new google.maps.DirectionsService();

    directionsService.route(
      {
        origin: startPoint,  // Starting point
        destination: bins[bins.length - 1].position,  // Last bin as destination
        travelMode: google.maps.TravelMode.DRIVING,
        waypoints: bins.slice(0, bins.length - 1).map(bin => ({ location: bin.position })),  // All bins except the last as waypoints
        optimizeWaypoints: true,  // Optimizes the waypoints for shortest route
      },
      (result, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
          setDirectionsResponse(result);
          const route = result.routes[0].legs.reduce((acc, leg) => {
            acc.distance += leg.distance.value;
            acc.time += leg.duration.value;
            return acc;
          }, { distance: 0, time: 0 });
          setRouteDetails({
            distance: (route.distance / 1000).toFixed(2),  // Distance in km
            time: Math.round(route.time / 60),  // Time in minutes
          });
        } else {
          console.error('Error fetching directions', result);
        }
      }
    );
  };

  return (
    <div className="flex flex-col h-full">
      <h1 className="text-2xl font-bold mb-4">Route Map</h1>
      <div className="flex-1 bg-white shadow rounded-lg overflow-hidden">
        <LoadScript googleMapsApiKey="AIzaSyCtV803a3BAeHMRNxe0QVsQxC83ZGHO16k">
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={center}
            zoom={13}
            onLoad={calculateRoute}
            ref={mapRef}
          >
            {directionsResponse && <DirectionsRenderer directions={directionsResponse} />}
          </GoogleMap>
        </LoadScript>
      </div>
      <div className="mt-4 bg-white shadow rounded-lg p-4">
        <h2 className="text-xl font-semibold mb-2">Route Details:</h2>
        <ul className="list-disc list-inside text-sm">
          <li>Total distance: {routeDetails.distance} km</li>
          <li>Estimated time: {routeDetails.time} minutes</li>
          <li>Number of bins: {bins.length}</li>
          <li>Areas covered: Colombo Fort, Colombo 02, Colombo 03</li>
        </ul>
      </div>
    </div>
  );
};

export default RouteMap;