import axios from "axios";

const token = sessionStorage.getItem('token');

//creating a interceptor for attach token
axios.interceptors.request.use(
    (config) => {
        if (token){
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    }
)


const REST_GET_ALL_SPECIAL_REQUESTS = "http://localhost:8081/test/http://localhost:8081/test/getAllSpecialWastes";


export const fetchSpecialRequests = () => 
    axios.get(REST_GET_ALL_SPECIAL_REQUESTS);