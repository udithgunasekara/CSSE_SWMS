import axios, { AxiosResponse } from "axios";

const REST_API_BASE_URL = 'http://localhost:8081/api/trashbin';

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

export const getToCollectBins = (): Promise<TrashBin[]> => {
    return axios
      .get<TrashBin[]>(REST_API_BASE_URL)
      .then(response => response.data);  // Extract the data from the response
  };
  