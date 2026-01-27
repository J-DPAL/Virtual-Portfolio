import apiClient from './apiClient';

export const getAllEducation = () => apiClient.get('/v1/education');

export const getEducationById = (id) => apiClient.get(`/v1/education/${id}`);

export const createEducation = (educationData) =>
  apiClient.post('/v1/education', educationData);

export const updateEducation = (id, educationData) =>
  apiClient.put(`/v1/education/${id}`, educationData);

export const deleteEducation = (id) => apiClient.delete(`/v1/education/${id}`);
