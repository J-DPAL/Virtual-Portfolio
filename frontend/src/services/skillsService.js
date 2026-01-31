import apiClient from './apiClient';

export const getAllSkills = () => apiClient.get('/skills');

export const getSkillById = (id) => apiClient.get(`/skills/${id}`);

export const createSkill = (skillData) => apiClient.post('/skills', skillData);

export const updateSkill = (id, skillData) =>
  apiClient.put(`/skills/${id}`, skillData);

export const deleteSkill = (id) => apiClient.delete(`/skills/${id}`);
