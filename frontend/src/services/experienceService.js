import apiClient from './apiClient';

export const getAllExperiences = () => apiClient.get('/experience');

export const getExperienceById = (id) => apiClient.get(`/experience/${id}`);

export const createExperience = (experienceData) =>
  apiClient.post('/experience', experienceData);

export const updateExperience = (id, experienceData) =>
  apiClient.put(`/experience/${id}`, experienceData);

export const deleteExperience = (id) =>
  apiClient.delete(`/experience/${id}`);
