import apiClient from './apiClient';

export const getAllEducation = () => apiClient.get('/education');

export const getEducationById = (id) => apiClient.get(`/education/${id}`);

export const createEducation = (educationData) =>
  apiClient.post('/education', educationData);

export const updateEducation = (id, educationData) =>
  apiClient.put(`/education/${id}`, educationData);

export const deleteEducation = (id) => apiClient.delete(`/education/${id}`);
