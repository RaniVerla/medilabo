import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: {
        "Content-Type": "application/json"
    }
});

api.interceptors.request.use(
  (config) => {
    // Retrieve the token from wherever you store it (localStorage, state, etc.)
    const token = localStorage.getItem('authToken'); 

    // If the token exists, inject it into the Authorization header
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    
    return config;
  },
  (error) => {
    // Handle request errors
    return Promise.reject(error);
  }
);

export default api;