import React, { useEffect, useRef, useState } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import 'leaflet-routing-machine';
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';

// Fix for default marker icons
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

const RouteMap = () => {
  const mapRef = useRef(null);
  const [map, setMap] = useState(null);
  const [routingControl, setRoutingControl] = useState(null);
  const [routeDetails, setRouteDetails] = useState({ distance: 0, time: 0 });

  const bins = [
    { position: [6.9271, 79.8612], name: "Bin 1 (Fort)" },
    { position: [6.9286, 79.8473], name: "Bin 2 (Colombo 02)" },
    { position: [6.9309, 79.8428], name: "Bin 3 (Colombo 03)" },
  ];

  useEffect(() => {
    if (!mapRef.current) return;

    const newMap = L.map(mapRef.current, {
      zoomControl: false,
      tap: true,
    }).setView([6.9271, 79.8612], 13);
    
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(newMap);

    L.control.zoom({
      position: 'bottomright'
    }).addTo(newMap);

    setMap(newMap);

    return () => {
      newMap.remove();
    };
  }, []);

  useEffect(() => {
    if (!map) return;

    const newRoutingControl = L.Routing.control({
      waypoints: bins.map(bin => L.latLng(bin.position)),
      routeWhileDragging: false,
      show: false,
      addWaypoints: false,
      draggableWaypoints: false,
      fitSelectedRoutes: true,
    }).addTo(map);

    newRoutingControl.on('routesfound', (e) => {
      const routes = e.routes;
      const summary = routes[0].summary;
      setRouteDetails({
        distance: (summary.totalDistance / 1000).toFixed(2),
        time: Math.round(summary.totalTime / 60)
      });
    });

    bins.forEach(bin => {
      L.marker(bin.position)
        .addTo(map)
        .bindPopup(bin.name);
    });

    setRoutingControl(newRoutingControl);

    return () => {
      map.removeControl(newRoutingControl);
    };
  }, [map]);

  useEffect(() => {
    const handleResize = () => {
      if (map) {
        map.invalidateSize();
      }
    };

    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, [map]);

  return (
    <div className="flex flex-col h-full">
      <h1 className="text-2xl font-bold mb-4">Route Map</h1>
      <div className="flex-1 bg-white shadow rounded-lg overflow-hidden">
        <div ref={mapRef} className="h-full w-full"></div>
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