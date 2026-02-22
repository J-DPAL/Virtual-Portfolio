function toIso(value) {
  if (!value) return null;
  const d = value instanceof Date ? value : new Date(value);
  return Number.isNaN(d.getTime()) ? null : d.toISOString();
}

export function mapProjectRow(row) {
  if (!row) return row;
  const technologies = row.technologies || '';
  return {
    id: row.id,
    titleEn: row.title_en,
    titleFr: row.title_fr,
    titleEs: row.title_es,
    descriptionEn: row.description_en,
    descriptionFr: row.description_fr,
    descriptionEs: row.description_es,
    technologies,
    projectUrl: row.project_url,
    githubUrl: row.github_url,
    imageUrl: row.image_url,
    status: row.status,
    startDate: row.start_date,
    endDate: row.end_date,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat with existing admin UI field names.
    liveLink: row.project_url,
    githubLink: row.github_url,
  };
}

export function mapProjectDtoToRow(dto) {
  if (!dto) return dto;
  const technologies = Array.isArray(dto.technologies)
    ? dto.technologies.join(', ')
    : dto.technologies;

  return {
    title_en: dto.titleEn ?? '',
    title_fr: dto.titleFr ?? '',
    title_es: dto.titleEs ?? '',
    description_en: dto.descriptionEn ?? null,
    description_fr: dto.descriptionFr ?? null,
    description_es: dto.descriptionEs ?? null,
    technologies: technologies ?? null,
    project_url: dto.projectUrl ?? dto.liveLink ?? null,
    github_url: dto.githubUrl ?? dto.githubLink ?? null,
    image_url: dto.imageUrl ?? null,
    start_date: dto.startDate ?? null,
    end_date: dto.endDate ?? null,
    status: dto.status ?? 'Completed',
  };
}

export function mapSkillRow(row) {
  if (!row) return row;
  const proficiency = (row.proficiency_level || '').toLowerCase();
  return {
    id: row.id,
    nameEn: row.name_en,
    nameFr: row.name_fr,
    nameEs: row.name_es,
    descriptionEn: row.description_en,
    descriptionFr: row.description_fr,
    descriptionEs: row.description_es,
    proficiencyLevel: row.proficiency_level,
    category: row.category,
    yearsOfExperience: row.years_of_experience,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat with existing admin UI field names.
    proficiency: proficiency || 'intermediate',
  };
}

export function mapSkillDtoToRow(dto) {
  if (!dto) return dto;
  const level = (dto.proficiencyLevel ?? dto.proficiency ?? 'intermediate')
    .toString()
    .toLowerCase();

  return {
    name_en: dto.nameEn ?? '',
    name_fr: dto.nameFr ?? '',
    name_es: dto.nameEs ?? '',
    description_en: dto.descriptionEn ?? null,
    description_fr: dto.descriptionFr ?? null,
    description_es: dto.descriptionEs ?? null,
    proficiency_level: level,
    category: dto.category ?? 'General',
    years_of_experience: dto.yearsOfExperience ?? null,
  };
}

export function mapEducationRow(row) {
  if (!row) return row;
  return {
    id: row.id,
    institutionNameEn: row.institution_name_en,
    institutionNameFr: row.institution_name_fr,
    institutionNameEs: row.institution_name_es,
    degreeEn: row.degree_en,
    degreeFr: row.degree_fr,
    degreeEs: row.degree_es,
    fieldOfStudyEn: row.field_of_study_en,
    fieldOfStudyFr: row.field_of_study_fr,
    fieldOfStudyEs: row.field_of_study_es,
    descriptionEn: row.description_en,
    descriptionFr: row.description_fr,
    descriptionEs: row.description_es,
    startDate: row.start_date,
    endDate: row.end_date,
    isCurrent: row.is_current,
    gpa: row.gpa,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat with admin UI field names.
    institutionEn: row.institution_name_en,
    institutionFr: row.institution_name_fr,
    institutionEs: row.institution_name_es,
    fieldEn: row.field_of_study_en,
    fieldFr: row.field_of_study_fr,
    fieldEs: row.field_of_study_es,
    grade: row.gpa,
    location: null,
  };
}

export function mapEducationDtoToRow(dto) {
  if (!dto) return dto;
  return {
    institution_name_en: dto.institutionNameEn ?? dto.institutionEn ?? '',
    institution_name_fr: dto.institutionNameFr ?? dto.institutionFr ?? '',
    institution_name_es: dto.institutionNameEs ?? dto.institutionEs ?? '',
    degree_en: dto.degreeEn ?? '',
    degree_fr: dto.degreeFr ?? '',
    degree_es: dto.degreeEs ?? '',
    field_of_study_en: dto.fieldOfStudyEn ?? dto.fieldEn ?? '',
    field_of_study_fr: dto.fieldOfStudyFr ?? dto.fieldFr ?? '',
    field_of_study_es: dto.fieldOfStudyEs ?? dto.fieldEs ?? '',
    description_en: dto.descriptionEn ?? null,
    description_fr: dto.descriptionFr ?? null,
    description_es: dto.descriptionEs ?? null,
    start_date: dto.startDate ?? null,
    end_date: dto.endDate ?? null,
    is_current: dto.isCurrent ?? false,
    gpa: dto.gpa ?? dto.grade ?? null,
  };
}

export function mapExperienceRow(row) {
  if (!row) return row;
  return {
    id: row.id,
    companyNameEn: row.company_name_en,
    companyNameFr: row.company_name_fr,
    companyNameEs: row.company_name_es,
    positionEn: row.position_en,
    positionFr: row.position_fr,
    positionEs: row.position_es,
    descriptionEn: row.description_en,
    descriptionFr: row.description_fr,
    descriptionEs: row.description_es,
    locationEn: row.location_en,
    locationFr: row.location_fr,
    locationEs: row.location_es,
    startDate: row.start_date,
    endDate: row.end_date,
    isCurrent: row.is_current,
    skillsUsed: row.skills_used,
    icon: row.icon,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat with admin UI field names.
    companyEn: row.company_name_en,
    companyFr: row.company_name_fr,
    companyEs: row.company_name_es,
    location: row.location_en ?? row.location_fr ?? row.location_es ?? null,
  };
}

export function mapExperienceDtoToRow(dto) {
  if (!dto) return dto;
  const locationFallback = dto.location ?? null;
  return {
    company_name_en: dto.companyNameEn ?? dto.companyEn ?? '',
    company_name_fr: dto.companyNameFr ?? dto.companyFr ?? '',
    company_name_es: dto.companyNameEs ?? dto.companyEs ?? '',
    position_en: dto.positionEn ?? '',
    position_fr: dto.positionFr ?? '',
    position_es: dto.positionEs ?? '',
    description_en: dto.descriptionEn ?? null,
    description_fr: dto.descriptionFr ?? null,
    description_es: dto.descriptionEs ?? null,
    location_en: dto.locationEn ?? locationFallback,
    location_fr: dto.locationFr ?? locationFallback,
    location_es: dto.locationEs ?? locationFallback,
    start_date: dto.startDate ?? null,
    end_date: dto.endDate ?? null,
    is_current: dto.isCurrent ?? false,
    skills_used: dto.skillsUsed ?? null,
    icon: dto.icon ?? null,
  };
}

export function mapHobbyRow(row) {
  if (!row) return row;
  return {
    id: row.id,
    nameEn: row.name_en,
    nameFr: row.name_fr,
    nameEs: row.name_es,
    descriptionEn: row.description_en,
    descriptionFr: row.description_fr,
    descriptionEs: row.description_es,
    icon: row.icon,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),
  };
}

export function mapHobbyDtoToRow(dto) {
  if (!dto) return dto;
  return {
    name_en: dto.nameEn ?? '',
    name_fr: dto.nameFr ?? '',
    name_es: dto.nameEs ?? '',
    description_en: dto.descriptionEn ?? null,
    description_fr: dto.descriptionFr ?? null,
    description_es: dto.descriptionEs ?? null,
    icon: dto.icon ?? null,
  };
}

export function mapTestimonialRow(row) {
  if (!row) return row;
  const status = (row.status || '').toUpperCase();
  return {
    id: row.id,
    clientName: row.client_name,
    clientPosition: row.client_position,
    clientCompany: row.client_company,
    testimonialTextEn: row.testimonial_text_en,
    testimonialTextFr: row.testimonial_text_fr,
    testimonialTextEs: row.testimonial_text_es,
    rating: row.rating,
    clientImageUrl: row.client_image_url,
    status,
    approved: status === 'APPROVED',
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat with admin UI, which expects `content`.
    content: row.testimonial_text_en,
  };
}

export function mapTestimonialDtoToRow(dto) {
  if (!dto) return dto;
  return {
    client_name: dto.clientName ?? '',
    client_position: dto.clientPosition ?? '',
    client_company: dto.clientCompany ?? '',
    testimonial_text_en: dto.testimonialTextEn ?? '',
    testimonial_text_fr: dto.testimonialTextFr ?? '',
    testimonial_text_es: dto.testimonialTextEs ?? '',
    rating: dto.rating ?? 5,
    client_image_url: dto.clientImageUrl ?? null,
    status: dto.status ?? 'PENDING',
  };
}

export function mapMessageRow(row) {
  if (!row) return row;
  return {
    id: row.id,
    name: row.sender_name,
    email: row.sender_email,
    subject: row.subject,
    message: row.message,
    isRead: row.is_read,
    createdAt: toIso(row.created_at),
    updatedAt: toIso(row.updated_at),

    // Back-compat fields used in some admin components
    content: row.message,
    date: row.created_at,
  };
}
