import apiClient from './apiClient';
import {
  mapProjectDtoToRow,
  mapProjectRow,
} from './dbMappers';
import {
  assertSupabaseConfigured,
  isSupabaseConfigured,
  supabase,
} from './supabaseClient';

function toAxiosLikeResponse(data) {
  return { data };
}

export const getAllProjects = async () => {
  if (!isSupabaseConfigured()) {
    const response = await apiClient.get('/projects');
    return toAxiosLikeResponse((response.data || []).map(mapProjectRow));
  }

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('projects')
    .select('*')
    .order('created_at', { ascending: false });
  if (error) throw error;
  return toAxiosLikeResponse((data || []).map(mapProjectRow));
};

export const getProjectById = async (id) => {
  if (!isSupabaseConfigured()) {
    const response = await apiClient.get(`/projects/${id}`);
    return toAxiosLikeResponse(mapProjectRow(response.data));
  }

  assertSupabaseConfigured();
  const { data, error } = await supabase
    .from('projects')
    .select('*')
    .eq('id', id)
    .maybeSingle();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const createProject = async (projectData) => {
  if (!isSupabaseConfigured())
    return apiClient.post('/projects', {
      titleEn: projectData.titleEn ?? '',
      titleFr: projectData.titleFr ?? '',
      titleEs: projectData.titleEs ?? '',
      descriptionEn: projectData.descriptionEn ?? null,
      descriptionFr: projectData.descriptionFr ?? null,
      descriptionEs: projectData.descriptionEs ?? null,
      technologies: Array.isArray(projectData.technologies)
        ? projectData.technologies.join(', ')
        : projectData.technologies ?? null,
      projectUrl: projectData.projectUrl ?? projectData.liveLink ?? null,
      githubUrl: projectData.githubUrl ?? projectData.githubLink ?? null,
      imageUrl: projectData.imageUrl ?? null,
      startDate: projectData.startDate ?? null,
      endDate: projectData.endDate ?? null,
      status: projectData.status ?? 'Completed',
    });

  assertSupabaseConfigured();
  const row = mapProjectDtoToRow(projectData);
  const { data, error } = await supabase
    .from('projects')
    .insert(row)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const updateProject = async (id, projectData) => {
  if (!isSupabaseConfigured())
    return apiClient.put(`/projects/${id}`, {
      titleEn: projectData.titleEn ?? '',
      titleFr: projectData.titleFr ?? '',
      titleEs: projectData.titleEs ?? '',
      descriptionEn: projectData.descriptionEn ?? null,
      descriptionFr: projectData.descriptionFr ?? null,
      descriptionEs: projectData.descriptionEs ?? null,
      technologies: Array.isArray(projectData.technologies)
        ? projectData.technologies.join(', ')
        : projectData.technologies ?? null,
      projectUrl: projectData.projectUrl ?? projectData.liveLink ?? null,
      githubUrl: projectData.githubUrl ?? projectData.githubLink ?? null,
      imageUrl: projectData.imageUrl ?? null,
      startDate: projectData.startDate ?? null,
      endDate: projectData.endDate ?? null,
      status: projectData.status ?? 'Completed',
    });

  assertSupabaseConfigured();
  const row = mapProjectDtoToRow(projectData);
  const { data, error } = await supabase
    .from('projects')
    .update(row)
    .eq('id', id)
    .select('*')
    .single();
  if (error) throw error;
  return toAxiosLikeResponse(mapProjectRow(data));
};

export const deleteProject = async (id) => {
  if (!isSupabaseConfigured()) return apiClient.delete(`/projects/${id}`);

  assertSupabaseConfigured();
  const { error } = await supabase.from('projects').delete().eq('id', id);
  if (error) throw error;
  return toAxiosLikeResponse({ ok: true });
};
