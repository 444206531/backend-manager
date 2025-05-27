import axiosInstance from './axiosInstance';
import type { User } from '../types/user'; // Define User type later or use 'any' for now

// The User interface is defined in ../types/user.d.ts and imported above.
// No need to redefine it here.

const API_URL = '/users'; // Relative to the baseURL in axiosInstance

export const getAllUsers = (): Promise<User[]> => {
  return axiosInstance.get<User[]>(API_URL).then(response => response.data);
};

export const getUserById = (id: number | string): Promise<User> => {
  return axiosInstance.get<User>(`${API_URL}/${id}`).then(response => response.data);
};

export const createUser = (user: User): Promise<User> => {
  return axiosInstance.post<User>(API_URL, user).then(response => response.data);
};

export const updateUser = (id: number | string, user: User): Promise<User> => {
  return axiosInstance.put<User>(`${API_URL}/${id}`, user).then(response => response.data);
};

export const deleteUser = (id: number | string): Promise<void> => {
  return axiosInstance.delete<void>(`${API_URL}/${id}`).then(response => response.data);
};
