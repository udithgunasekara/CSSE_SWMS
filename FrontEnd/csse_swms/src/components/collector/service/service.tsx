import axios from "axios";

// const token = sessionStorage.getItem('token');

//reating a interceptor for attach token
// axios.interceptors.request.use(
//     (config) => {
//         if (token){
//             config.headers.Authorization = `Bearer ${token}`;
//         }
//         return config;
//     }
// )


// we can use interceptor as well without setting defaults headers
const token = sessionStorage.getItem('token');
axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;



const REST_GET_ALL_SPECIAL_REQUESTS = "http://localhost:8081/test/getAllSpecialWastes";
const REST_USER_LOGIN = "http://localhost:8081/login"


export const userLogin = (formData : any ) => axios.post(REST_USER_LOGIN, formData);

export const fetchSpecialRequests = () => 
    axios.get(REST_GET_ALL_SPECIAL_REQUESTS);