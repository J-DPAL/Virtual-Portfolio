import apiClient from './apiClient';

export const getAllExperiences = () => apiClient.get('/v1/experiences');

export const getExperienceById = (id) => apiClient.get(`/v1/experiences/${id}`);

export const createExperience = (experienceData) =>
  apiClient.post('/v1/experiences', experienceData);

export const updateExperience = (id, experienceData) =>
  apiClient.put(`/v1/experiences/${id}`, experienceData);

export const deleteExperience = (id) =>
  apiClient.delete(`/v1/experiences/${id}`);
