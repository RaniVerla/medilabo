import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json"
    }
});

// Request Interceptor
// Adds JWT token to every request
api.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("authToken");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response Interceptor
// If token is expired or invalid, logout automatically
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {

        if (error.response?.status === 401) {

            console.log("Session expired. Redirecting to Login...");

            // Remove expired token
            localStorage.removeItem("authToken");

            // Redirect to Login page
            window.location.href = "/";
        }

        return Promise.reject(error);
    }
);

export default api;