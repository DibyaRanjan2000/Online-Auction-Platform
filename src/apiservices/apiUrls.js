// import axios from "axios";

// // Define the base URL for your API
// const url = "http://localhost:8081/";

// // Create an Axios instance with a base URL
// const API = axios.create({
//   baseURL: url,
//   headers: {
//     "Content-Type": "application/json",
//   },
// });

// // Function to get the token from localStorage
// const getAuthToken = () => {
//   return localStorage.getItem("authToken"); // Retrieve the token from localStorage
// };

// // Interceptor to add token to headers before each request
// API.interceptors.request.use(
//   (config) => {
//     const token = getAuthToken(); // Get token from localStorage
//     if (token) {
//       // If token exists, add it to Authorization header
//       config.headers["Authorization"] = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error); // Handle error
//   }
// );

// const apiService = {
//   // GET request
//   get: async (url, config = {}) => {
//     try {
//       const response = await API.get(url, config);
//       console.log("GET response:", JSON.stringify(response?.data));
//       return response?.data; // Return the data from response
//     } catch (e) {
//       console.log("Unable to fetch data ", e);
//       throw e; // Propagate error for handling in the component
//     }
//   },

//   // POST request
//   post: async (url, data, config = {}) => {
//     try {
//       const response = await API.post(url, data, config);
//       console.log("POST response:", JSON.stringify(response?.data));
//       return response?.data; // Return the data from response
//     } catch (e) {
//       console.log("Unable to upload data ", e);
//       throw e; // Propagate error for handling in the component
//     }
//   },

//   // PUT request
//   put: async (url, data, config = {}) => {
//     try {
//       const response = await API.put(url, data, config);
//       console.log("PUT response:", JSON.stringify(response?.data));
//       return response?.data; // Return the data from response
//     } catch (e) {
//       console.log("Unable to update data ", e);
//       throw e; // Propagate error for handling in the component
//     }
//   },

//   // DELETE request
//   delete: async (url, config = {}) => {
//     try {
//       const response = await API.delete(url, config);
//       console.log("DELETE response:", JSON.stringify(response?.data));
//       return response?.data; // Return the data from response
//     } catch (e) {
//       console.log("Unable to delete data ", e);
//       throw e; // Propagate error for handling in the component
//     }
//   },
// };

// export default apiService;


import axios from "axios";

// Base URL for your API
const url = "http://localhost:8081";

// Create an Axios instance
const API = axios.create({
  baseURL: url,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,  // Add this if your backend uses cookies or requires credentials
});

// Function to retrieve token from localStorage
const getAuthToken = () => {
  return localStorage.getItem("authToken"); // Retrieve the token from localStorage
};

// Interceptor to add Authorization token to headers before each request
API.interceptors.request.use(
  (config) => {
    const token = getAuthToken(); // Get token from localStorage
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // Add token to Authorization header
    }
    return config; // Return the modified config
  },
  (error) => {
    return Promise.reject(error); // Reject the error if there's an issue
  }
);

// Interceptor to handle error responses globally
API.interceptors.response.use(
  (response) => response, // Just return the response if successful
  (error) => {
    if (error.response) {
      // If we have a response from the server
      const { status } = error.response;

      // If 403 or 401 error occurs (authentication/authorization related)
      if (status === 403 || status === 401) {
        console.log("Authorization error: Please log in again.");
        // Optionally clear token if it's expired or invalid
        // localStorage.removeItem("authToken");
      }

      console.error(`Error: ${status}`, error.response.data); // Log detailed error response
    } else {
      // If no response from server, handle network errors
      console.error("Network error: Unable to connect to the backend.");
    }

    return Promise.reject(error); // Propagate error
  }
);

// API service to handle different HTTP requests
const apiService = {
  // GET request
  get: async (endpoint, config = {}) => {
    try {
      const response = await API.get(endpoint, config); // Make the GET request
      console.log("GET response:", JSON.stringify(response?.data)); // Log response data
      return response?.data; // Return the response data
    } catch (e) {
      console.log("Unable to fetch data:", e); // Error handling
      throw e; // Propagate error
    }
  },

  // POST request
  post: async (endpoint, data, config = {}) => {
    try {
      debugger;
      const response = await API.post(endpoint, data, config); // Make the POST request
      console.log("POST response:", JSON.stringify(response?.data)); // Log response data
      return response?.data; // Return the response data
    } catch (e) {
      console.log("Unable to upload data:", e); // Error handling
      throw e; // Propagate error
    }
  },

  // PUT request
  put: async (endpoint, data, config = {}) => {
    try {
      const response = await API.put(endpoint, data, config); // Make the PUT request
      console.log("PUT response:", JSON.stringify(response?.data)); // Log response data
      return response?.data; // Return the response data
    } catch (e) {
      console.log("Unable to update data:", e); // Error handling
      throw e; // Propagate error
    }
  },

  // DELETE request
  delete: async (endpoint, config = {}) => {
    try {
      const response = await API.delete(endpoint, config); // Make the DELETE request
      console.log("DELETE response:", JSON.stringify(response?.data)); // Log response data
      return response?.data; // Return the response data
    } catch (e) {
      console.log("Unable to delete data:", e); // Error handling
      throw e; // Propagate error
    }
  },

  // Save token to localStorage
  saveToken: (token) => {
    localStorage.setItem("authToken", token); // Save token to localStorage
  },

  // Remove token from localStorage
  removeToken: () => {
    localStorage.removeItem("authToken"); // Remove token from localStorage
  },

  // Check if token exists in localStorage
  isAuthenticated: () => {
    return !!localStorage.getItem("authToken"); // Check if token exists
  },
};

export default apiService;
