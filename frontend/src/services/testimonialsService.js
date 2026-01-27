import apiClient from './apiClient';

export const getAllTestimonials = () => apiClient.get('/v1/testimonials');

export const getApprovedTestimonials = () =>
  apiClient.get('/v1/testimonials/approved');

export const getTestimonialById = (id) =>
  apiClient.get(`/v1/testimonials/${id}`);

export const submitTestimonial = (testimonialData) =>
  apiClient.post('/v1/testimonials', testimonialData);

export const updateTestimonial = (id, testimonialData) =>
  apiClient.put(`/v1/testimonials/${id}`, testimonialData);

export const approveTestimonial = (id) =>
  apiClient.patch(`/v1/testimonials/${id}/approve`);

export const deleteTestimonial = (id) =>
  apiClient.delete(`/v1/testimonials/${id}`);
