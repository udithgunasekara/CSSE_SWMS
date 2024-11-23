import React, { useState, useEffect, useRef } from 'react';
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

const RouteManagement = () => {
  const [routes, setRoutes] = useState([
    { 
      id: 1, 
      name: 'Downtown Route', 
      bins: 20, 
      distance: '15 km', 
      efficiency: 'High',
      waypoints: [
        [6.9271, 79.8612],
        [6.9286, 79.8473],
        [6.9309, 79.8428]
      ]
    },
    { 
      id: 2, 
      name: 'Suburban Route A', 
      bins: 15, 
      distance: '25 km', 
      efficiency: 'Medium',
      waypoints: [
        [6.9200, 79.8550],
        [6.9250, 79.8600],
        [6.9300, 79.8650]
      ]
    },
    { 
      id: 3, 
      name: 'Industrial Park Route', 
      bins: 10, 
      distance: '10 km', 
      efficiency: 'High',
      waypoints: [
        [6.9350, 79.8700],
        [6.9400, 79.8750],
        [6.9450, 79.8800]
      ]
    },
  ]);

  const [selectedRoute, setSelectedRoute] = useState(null);
  const [map, setMap] = useState(null);
  const [routingControl, setRoutingControl] = useState(null);
  const mapRef = useRef(null);

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
    if (!map || !selectedRoute) return;

    if (routingControl) {
      map.removeControl(routingControl);
    }

    const newRoutingControl = L.Routing.control({
      waypoints: selectedRoute.waypoints.map(wp => L.latLng(wp)),
      routeWhileDragging: false,
      show: false,
      addWaypoints: false,
      draggableWaypoints: false,
      fitSelectedRoutes: true,
    }).addTo(map);

    setRoutingControl(newRoutingControl);

    return () => {
      if (routingControl) {
        map.removeControl(routingControl);
      }
    };
  }, [map, selectedRoute]);

  const handleRouteSelect = (route) => {
    setSelectedRoute(route);
  };

  const getEfficiencyColor = (efficiency) => {
    switch (efficiency) {
      case 'High': return 'bg-green-100 text-green-800';
      case 'Medium': return 'bg-yellow-100 text-yellow-800';
      default: return 'bg-red-100 text-red-800';
    }
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Route Management</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Route Map</h2>
          <div ref={mapRef} className="h-64 rounded-lg"></div>
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Route List</h2>
          <ul className="space-y-2">
            {routes.map((route) => (
              <li 
                key={route.id} 
                className={`flex justify-between items-center border-b pb-2 cursor-pointer hover:bg-gray-50 ${selectedRoute?.id === route.id ? 'bg-gray-100' : ''}`}
                onClick={() => handleRouteSelect(route)}
              >
                <span className="font-medium">{route.name}</span>
                <span className="text-sm text-gray-500">{route.bins} bins | {route.distance}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div className="bg-white shadow rounded-lg overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Route Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Number of Bins</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Distance</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Efficiency</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {routes.map((route) => (
              <tr 
                key={route.id}
                className={`cursor-pointer hover:bg-gray-50 ${selectedRoute?.id === route.id ? 'bg-gray-100' : ''}`}
                onClick={() => handleRouteSelect(route)}
              >
                <td className="px-6 py-4 whitespace-nowrap">{route.name}</td>
                <td className="px-6 py-4 whitespace-nowrap">{route.bins}</td>
                <td className="px-6 py-4 whitespace-nowrap">{route.distance}</td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getEfficiencyColor(route.efficiency)}`}>
                    {route.efficiency}
                  </span>     
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RouteManagement;