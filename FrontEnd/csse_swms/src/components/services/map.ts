import axios, { AxiosResponse } from "axios";

const REST_API_BASE_URL = 'http://localhost:8081/api/trashbin';
const REST_API_BASE_URL_FACILITY = 'http://localhost:8081/api/profacility';

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

export const getToCollectBins = (facilityId: String): Promise<TrashBin[]> => {
    return axios
      .get<TrashBin[]>(`${REST_API_BASE_URL}/${facilityId}`)
      .then(response => response.data);  // Extract the data from the response
  };

export const getfacilitybyId = (facilityId: String): Promise<Facility> => {
    return axios
      .get<Facility>(`${REST_API_BASE_URL_FACILITY}/${facilityId}`)
      .then(response => response.data);  // Extract the data from the response
  };
  