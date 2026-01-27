import apiClient from './apiClient';

export const getAllHobbies = () => apiClient.get('/v1/hobbies');

export const getHobbyById = (id) => apiClient.get(`/v1/hobbies/${id}`);

export const createHobby = (hobbyData) =>
  apiClient.post('/v1/hobbies', hobbyData);

export const updateHobby = (id, hobbyData) =>
  apiClient.put(`/v1/hobbies/${id}`, hobbyData);

export const deleteHobby = (id) => apiClient.delete(`/v1/hobbies/${id}`);
