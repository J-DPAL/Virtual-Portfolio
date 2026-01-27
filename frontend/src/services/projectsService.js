import apiClient from './apiClient';

export const getAllProjects = () => apiClient.get('/v1/projects');

export const getProjectById = (id) => apiClient.get(`/v1/projects/${id}`);

export const createProject = (projectData) =>
  apiClient.post('/v1/projects', projectData);

export const updateProject = (id, projectData) =>
  apiClient.put(`/v1/projects/${id}`, projectData);

export const deleteProject = (id) => apiClient.delete(`/v1/projects/${id}`);
