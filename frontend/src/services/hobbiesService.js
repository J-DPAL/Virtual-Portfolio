import apiClient from './apiClient';

export const getAllHobbies = () => apiClient.get('/hobbies');

export const getHobbyById = (id) => apiClient.get(`/hobbies/${id}`);

export const createHobby = (hobbyData) =>
  apiClient.post('/hobbies', hobbyData);

export const updateHobby = (id, hobbyData) =>
  apiClient.put(`/hobbies/${id}`, hobbyData);

export const deleteHobby = (id) => apiClient.delete(`/hobbies/${id}`);
