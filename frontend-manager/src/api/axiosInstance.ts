import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: '/api', // Default base URL for backend API
  // timeout: 10000, // Optional: request timeout
  headers: {
    'Content-Type': 'application/json',
    // 'Authorization': 'Bearer your_token' // Example for token-based auth
  }
});

// Optional: Request interceptor (e.g., for adding auth tokens)
axiosInstance.interceptors.request.use(
  config => {
    // const token = localStorage.getItem('token');
    // if (token) {
    //   config.headers['Authorization'] = `Bearer ${token}`;
    // }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// Optional: Response interceptor (e.g., for global error handling)
axiosInstance.interceptors.response.use(
  response => {
    // You can transform response data here if needed
    return response;
  },
  error => {
    // Handle global errors, e.g., redirect to login on 401
    if (error.response && error.response.status === 401) {
      // console.error('Unauthorized, redirecting to login...');
      // router.push('/login'); // Assuming router is accessible or use window.location
    }
    // You might want to throw the error so that the calling code can handle it
    return Promise.reject(error);
  }
);

export default axiosInstance;
