import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { useTheme } from '../context/ThemeContext';
import { getAllProjects } from '../services/projectsService';
import BackToTopButton from '../components/BackToTopButton';
import manaProjectImage from '../assets/project-images/ManaProject.png';
import champlainPetClinicImage from '../assets/project-images/ChamplainPetClinicProject.png';
import wheatherAppProjectImage from '../assets/project-images/WheatherAppProject.png';

export default function ProjectsPage() {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedProject, setSelectedProject] = useState(null);
  const [filterTech, setFilterTech] = useState('');
  const [sortOption, setSortOption] = useState('newest');
  const [filteredProjects, setFilteredProjects] = useState([]);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchProjects();
  }, []);

  useEffect(() => {
    // Filtering and sorting logic
    let filtered = [...projects];
    if (filterTech) {
      filtered = filtered.filter(
        (project) =>
          project.technologies &&
          project.technologies.toLowerCase().includes(filterTech.toLowerCase())
      );
    }
    if (sortOption === 'alphabetical') {
      filtered.sort((a, b) => {
        const titleA = getProjectTitle(a).toLowerCase();
        const titleB = getProjectTitle(b).toLowerCase();
        return titleA.localeCompare(titleB);
      });
    } else if (sortOption === 'newest') {
      filtered.sort((a, b) => {
        // Assume projects have a 'createdAt' field
        return new Date(b.createdAt) - new Date(a.createdAt);
      });
    }
    setFilteredProjects(filtered);
  }, [projects, filterTech, sortOption]);

  const fetchProjects = async () => {
    try {
      setLoading(true);
      const response = await getAllProjects();
      setProjects(response.data);
    } catch (err) {
      setError(t('loadProjectsFailed'));
      console.error('Error fetching projects:', err);
    } finally {
      setLoading(false);
    }
  };

  const getProjectTitle = (project) =>
    currentLang === 'es' && project.titleEs
      ? project.titleEs
      : currentLang === 'fr' && project.titleFr
        ? project.titleFr
        : project.titleEn;

  const getProjectDescription = (project) =>
    currentLang === 'es' && project.descriptionEs
      ? project.descriptionEs
      : currentLang === 'fr' && project.descriptionFr
        ? project.descriptionFr
        : project.descriptionEn;

  const handleProjectOpen = (project) => {
    setSelectedProject(project);
  };

  const handleProjectClose = () => {
    setSelectedProject(null);
  };

  const getProjectVisual = (project) => {
    const title = `${project?.titleEn || ''} ${project?.titleFr || ''} ${
      project?.titleEs || ''
    }`;
    const description = `${project?.descriptionEn || ''} ${
      project?.descriptionFr || ''
    } ${project?.descriptionEs || ''}`;
    const tech = project?.technologies || '';
    const haystack = `${title} ${description} ${tech}`.toLowerCase();
    const hasWord = (value, word) =>
      new RegExp(`\\b${word.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}\\b`, 'i').test(value);

    if (hasWord(haystack, 'mana')) {
      return {
        gradient: 'from-cyan-500 to-blue-600',
        iconPath: 'M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4',
        imageSrc: manaProjectImage,
      };
    }

    if (haystack.includes('champlain') || haystack.includes('pet clinic')) {
      return {
        gradient: 'from-emerald-500 to-teal-600',
        iconPath:
          'M7 2h10a2 2 0 012 2v16a2 2 0 01-2 2H7a2 2 0 01-2-2V4a2 2 0 012-2zm5 16h.01M8 5h8',
        imageSrc: champlainPetClinicImage,
      };
    }

    if (
      haystack.includes('weather app') ||
      haystack.includes('wheather app')
    ) {
      return {
        gradient: 'from-sky-500 to-blue-600',
        iconPath:
          'M3 15a4 4 0 014-4 5 5 0 019.9 1.5A3.5 3.5 0 0118 19H7a4 4 0 01-4-4z',
        imageSrc: wheatherAppProjectImage,
      };
    }

    if (haystack.includes('mobile') || haystack.includes('android')) {
      return {
        gradient: 'from-emerald-500 to-teal-600',
        iconPath:
          'M7 2h10a2 2 0 012 2v16a2 2 0 01-2 2H7a2 2 0 01-2-2V4a2 2 0 012-2zm5 16h.01M8 5h8',
      };
    }

    if (haystack.includes('weather')) {
      return {
        gradient: 'from-sky-500 to-blue-600',
        iconPath:
          'M3 15a4 4 0 014-4 5 5 0 019.9 1.5A3.5 3.5 0 0118 19H7a4 4 0 01-4-4z',
      };
    }

    if (haystack.includes('e-commerce') || haystack.includes('commerce')) {
      return {
        gradient: 'from-orange-500 to-amber-500',
        iconPath:
          'M3 3h2l.4 2M7 13h10l4-8H5.4M7 13l-1.5 7H19M7 13l1.5 7M10 21a1 1 0 100-2 1 1 0 000 2zm8 1a1 1 0 100-2 1 1 0 000 2z',
      };
    }

    if (haystack.includes('game') || haystack.includes('unity')) {
      return {
        gradient: 'from-violet-500 to-indigo-600',
        iconPath:
          'M6 8h12a4 4 0 014 4v4a3 3 0 01-3 3h-2l-3-3H10l-3 3H5a3 3 0 01-3-3v-4a4 4 0 014-4zm2 4h2m-1-1v2m8-1h2',
      };
    }

    if (haystack.includes('microservice') || haystack.includes('rest')) {
      return {
        gradient: 'from-cyan-500 to-blue-600',
        iconPath: 'M4 6h6v6H4V6zm10 0h6v6h-6V6zM4 16h6v6H4v-6zm10 0h6v6h-6v-6z',
      };
    }

    if (haystack.includes('design') || haystack.includes('uml')) {
      return {
        gradient: 'from-rose-500 to-pink-600',
        iconPath: 'M4 6h16M4 12h10M4 18h7',
      };
    }

    return {
      gradient: 'from-blue-500 to-teal-600',
      iconPath: 'M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4',
    };
  };

  const techPatternStyle = {
    backgroundImage: isDark
      ? `linear-gradient(90deg, rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 18% 22%, rgba(34, 211, 238, 0.28) 0 2px, transparent 2px),
         radial-gradient(circle at 68% 68%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 82% 28%, rgba(56, 189, 248, 0.2) 0 1.5px, transparent 1.5px)`
      : `linear-gradient(90deg, rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 16% 24%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 72% 70%, rgba(14, 165, 233, 0.22) 0 2px, transparent 2px),
         radial-gradient(circle at 84% 26%, rgba(56, 189, 248, 0.18) 0 1.5px, transparent 1.5px)`,
    backgroundSize:
      '80px 80px, 80px 80px, 220px 220px, 260px 260px, 200px 200px',
    backgroundPosition: 'center, center, left top, right bottom, right top',
    maskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
    WebkitMaskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
  };

  const circuitPatternStyle = {
    backgroundImage: isDark
      ? `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%2322d3ee' stroke-width='1' opacity='0.35'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%2322d3ee' opacity='0.55'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`
      : `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%233b82f6' stroke-width='1' opacity='0.3'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%233b82f6' opacity='0.5'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`,
    backgroundSize: '260px 260px',
    backgroundPosition: 'center',
  };

  const dataStreamStyle = {
    backgroundImage: isDark
      ? `repeating-linear-gradient(120deg, rgba(45, 212, 191, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(59, 130, 246, 0.16) 0 1px, transparent 1px 18px)`
      : `repeating-linear-gradient(120deg, rgba(37, 99, 235, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(14, 165, 233, 0.16) 0 1px, transparent 1px 18px)`,
    backgroundSize: '100% 100%',
    maskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
    WebkitMaskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
  };

  const glowFieldStyle = {
    backgroundImage: isDark
      ? `radial-gradient(circle at 12% 18%, rgba(56, 189, 248, 0.35), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(99, 102, 241, 0.28), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(14, 165, 233, 0.25), transparent 50%)`
      : `radial-gradient(circle at 12% 18%, rgba(59, 130, 246, 0.25), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(14, 165, 233, 0.22), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(99, 102, 241, 0.18), transparent 50%)`,
  };

  if (loading) {
    return (
      <div
        className={`min-h-screen flex items-center justify-center transition-colors duration-200 ${
          isDark
            ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
            : 'bg-gradient-to-br from-blue-50 via-white to-teal-50'
        }`}
      >
        <div className="text-center">
          <div
            className={`inline-block animate-spin rounded-full h-12 w-12 border-b-2 ${
              isDark ? 'border-blue-400' : 'border-indigo-600'
            }`}
          ></div>
          <p className={`mt-4 ${isDark ? 'text-slate-400' : 'text-slate-600'}`}>
            {t('loading')}
          </p>
        </div>
      </div>
    );
  }

  return (
    <div
      className={`min-h-screen py-12 transition-colors duration-200 relative overflow-hidden ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
          : 'bg-gradient-to-br from-blue-50 via-white to-teal-50'
      }`}
    >
      <div className="pointer-events-none absolute inset-0" aria-hidden="true">
        <div
          className={`absolute -top-24 -right-24 h-80 w-80 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-blue-700' : 'bg-blue-300'
          }`}
        ></div>
        <div
          className={`absolute -bottom-32 -left-16 h-96 w-96 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-teal-700' : 'bg-teal-300'
          }`}
        ></div>
      </div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-60 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={techPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-35 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={circuitPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-40 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={dataStreamStyle}
      ></div>
      <div
        className="pointer-events-none absolute inset-0 opacity-60"
        aria-hidden="true"
        style={glowFieldStyle}
      ></div>
      <div className="container mx-auto px-4 relative z-10">
        <header className="text-center mb-12">
          {/* Filter & Sort Controls */}
          <div className="flex flex-col md:flex-row gap-4 mb-8 items-center justify-between max-w-7xl mx-auto">
            <div className="flex flex-col md:flex-row gap-4 w-full md:w-auto">
              <select
                className={`px-4 py-2 rounded-lg border focus:outline-none focus:ring-2 focus:ring-blue-400 transition text-sm ${
                  isDark
                    ? 'bg-slate-800 border-slate-700 text-slate-100'
                    : 'bg-slate-50 border-slate-300 text-slate-900'
                }`}
                value={filterTech}
                onChange={(e) => setFilterTech(e.target.value)}
              >
                <option value="">
                  {t('filterByTechnology')}
                </option>
                {/* Build unique tech list from projects */}
                {Array.from(
                  new Set(
                    projects.flatMap((p) =>
                      p.technologies
                        ? p.technologies.split(',').map((t) => t.trim())
                        : []
                    )
                  )
                )
                  .filter(Boolean)
                  .sort()
                  .map((tech) => (
                    <option key={tech} value={tech}>
                      {tech}
                    </option>
                  ))}
              </select>
              <select
                className={`px-4 py-2 rounded-lg border focus:outline-none focus:ring-2 focus:ring-blue-400 transition text-sm ${
                  isDark
                    ? 'bg-slate-800 border-slate-700 text-slate-100'
                    : 'bg-slate-50 border-slate-300 text-slate-900'
                }`}
                value={sortOption}
                onChange={(e) => setSortOption(e.target.value)}
              >
                <option value="newest">{t('sortNewest')}</option>
                <option value="alphabetical">
                  {t('sortAlphabetical')}
                </option>
              </select>
              {filterTech && (
                <button
                  className={`px-3 py-1 rounded border transition text-xs ${
                    isDark
                      ? 'bg-slate-800 border-slate-700 text-slate-200 hover:bg-slate-700'
                      : 'bg-slate-100 border-slate-300 text-slate-700 hover:bg-slate-200'
                  }`}
                  onClick={() => setFilterTech('')}
                >
                  {t('clearFilter')}
                </button>
              )}
            </div>
          </div>
          <span
            className={`inline-flex items-center px-4 py-1.5 rounded-full text-sm font-semibold border mb-4 ${
              isDark
                ? 'bg-slate-800/60 border-slate-700 text-slate-200'
                : 'bg-slate-50/70 border-slate-200 text-slate-700'
            }`}
          >
            {t('curatedWork')}
          </span>
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-teal-600 mb-4">
            {t('projects')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto mb-6 ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('explorePortfolioProjects')}
          </p>
          <Link
            to="/contact"
            className="inline-flex items-center px-6 py-3 rounded-xl bg-gradient-to-r from-blue-600 to-teal-600 text-white font-medium hover:shadow-xl hover:-translate-y-0.5 transition-all duration-200 shadow-lg shadow-blue-600/20"
          >
            {t('getInTouchButton')}
            <svg
              className="w-5 h-5 ml-2"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M17 8l4 4m0 0l-4 4m4-4H3"
              />
            </svg>
          </Link>
        </header>

        {error && (
          <div
            className={`max-w-2xl mx-auto mb-6 border px-4 py-3 rounded-lg ${
              isDark
                ? 'bg-red-900/30 border-red-700 text-red-300'
                : 'bg-red-50 border-red-200 text-red-700'
            }`}
          >
            {t('errorOccurred')}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-7xl mx-auto">
          {filteredProjects.map((project) => (
            <div
              key={project.id}
              onClick={() => handleProjectOpen(project)}
              onKeyDown={(event) => {
                if (event.key === 'Enter' || event.key === ' ') {
                  event.preventDefault();
                  handleProjectOpen(project);
                }
              }}
              role="button"
              tabIndex={0}
              className={`group relative rounded-3xl overflow-hidden hover:-translate-y-2 transition-all duration-300 ring-1 text-left w-full cursor-pointer ${
                isDark
                  ? 'bg-slate-900/70 ring-slate-800 hover:ring-blue-500/50'
                  : 'bg-slate-50/80 ring-slate-200 hover:ring-blue-400/40'
              } animate-fadeIn`}
            >
              <div className="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                <div className="absolute inset-0 bg-gradient-to-br from-blue-500/10 via-transparent to-teal-500/10"></div>
              </div>
              <div
                className={`bg-gradient-to-br ${getProjectVisual(project).gradient} h-48 flex items-center justify-center relative`}
              >
                {getProjectVisual(project).imageSrc ? (
                  <img
                    src={getProjectVisual(project).imageSrc}
                    alt={getProjectTitle(project)}
                    className="h-full w-full object-cover"
                    loading="lazy"
                  />
                ) : (
                  <svg
                    className="w-20 h-20 text-white opacity-80"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={1.5}
                      d={getProjectVisual(project).iconPath}
                    />
                  </svg>
                )}
              </div>
              <div className="p-6 backdrop-blur-sm">
                <h2
                  className={`text-2xl font-bold mb-3 group-hover:text-indigo-400 transition ${
                    isDark ? 'text-slate-100' : 'text-slate-900'
                  }`}
                >
                  {getProjectTitle(project)}
                </h2>
                <p
                  className={`mb-4 line-clamp-3 ${
                    isDark ? 'text-slate-400' : 'text-slate-600'
                  }`}
                >
                  {getProjectDescription(project)}
                </p>
                {project.technologies && (
                  <div className="flex flex-wrap gap-2 mb-4">
                    {project.technologies
                      .split(',')
                      .slice(0, 3)
                      .map((tech, idx) => (
                        <span
                          key={idx}
                          className={`text-xs px-3 py-1 rounded-full font-medium border ${
                            isDark
                              ? 'bg-indigo-900/30 text-indigo-300 border-indigo-700'
                              : 'bg-indigo-50 text-indigo-700 border-indigo-200'
                          }`}
                        >
                          {tech.trim()}
                        </span>
                      ))}
                  </div>
                )}
                {project.projectUrl && (
                  <a
                    href={project.projectUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    onClick={(event) => event.stopPropagation()}
                    className={`inline-flex items-center font-medium transition ${
                      isDark
                        ? 'text-indigo-300 hover:text-indigo-200'
                        : 'text-indigo-600 hover:text-indigo-700'
                    }`}
                  >
                    {t('viewProject')}
                    <svg
                      className="w-4 h-4 ml-1"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
                      />
                    </svg>
                  </a>
                )}
              </div>
            </div>
          ))}
        </div>
        {filteredProjects.length === 0 && !loading && (
          <div className="text-center py-12">
            <p
              className={`text-lg ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('noProjectsAvailable')}
            </p>
          </div>
        )}
      </div>

      {selectedProject && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center px-4 py-8"
          role="dialog"
          aria-modal="true"
          aria-label={getProjectTitle(selectedProject)}
        >
          <button
            type="button"
            className="absolute inset-0 bg-black/60"
            onClick={handleProjectClose}
            aria-label={t('closeProjectDetails')}
          ></button>
          <div
            className={`relative max-w-3xl w-full rounded-3xl shadow-2xl overflow-hidden ring-1 ${
              isDark
                ? 'bg-slate-900 ring-slate-800'
                : 'bg-slate-50 ring-slate-200'
            }`}
          >
            <div
              className={`bg-gradient-to-br ${getProjectVisual(selectedProject).gradient} h-28 flex items-center justify-between px-6`}
            >
              <div className="flex items-center gap-3 text-white">
                {getProjectVisual(selectedProject).imageSrc ? (
                  <img
                    src={getProjectVisual(selectedProject).imageSrc}
                    alt={getProjectTitle(selectedProject)}
                    className="h-12 w-12 rounded-lg object-cover"
                    loading="lazy"
                  />
                ) : (
                  <svg
                    className="w-10 h-10 opacity-90"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={1.5}
                      d={getProjectVisual(selectedProject).iconPath}
                    />
                  </svg>
                )}
                <div>
                  <h2 className="text-2xl font-bold">
                    {getProjectTitle(selectedProject)}
                  </h2>
                  <p className="text-white/80 text-sm">{t('projects')}</p>
                </div>
              </div>
              <button
                type="button"
                onClick={handleProjectClose}
                className="text-white/80 hover:text-white transition"
                aria-label={t('closeProjectDetails')}
              >
                <svg
                  className="w-6 h-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            </div>
            <div className="p-6 space-y-5 max-h-[70vh] overflow-y-auto">
              <p
                className={`text-base leading-relaxed ${
                  isDark ? 'text-slate-300' : 'text-slate-600'
                }`}
              >
                {getProjectDescription(selectedProject)}
              </p>

              {selectedProject.technologies && (
                <div className="flex flex-wrap gap-2">
                  {selectedProject.technologies.split(',').map((tech, idx) => (
                    <span
                      key={idx}
                      className={`text-xs px-3 py-1 rounded-full font-medium border ${
                        isDark
                          ? 'bg-indigo-900/30 text-indigo-300 border-indigo-700'
                          : 'bg-indigo-50 text-indigo-700 border-indigo-200'
                      }`}
                    >
                      {tech.trim()}
                    </span>
                  ))}
                </div>
              )}

              <div className="flex flex-wrap gap-4">
                {selectedProject.projectUrl && (
                  <a
                    href={selectedProject.projectUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={`inline-flex items-center font-semibold transition ${
                      isDark
                        ? 'text-indigo-300 hover:text-indigo-200'
                        : 'text-indigo-600 hover:text-indigo-700'
                    }`}
                  >
                    {t('viewProject')}
                    <svg
                      className="w-4 h-4 ml-1"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
                      />
                    </svg>
                  </a>
                )}
                {selectedProject.githubUrl && (
                  <a
                    href={selectedProject.githubUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={`inline-flex items-center font-semibold transition ${
                      isDark
                        ? 'text-emerald-300 hover:text-emerald-200'
                        : 'text-emerald-600 hover:text-emerald-700'
                    }`}
                  >
                    {t('viewGithubRepo')}
                    <svg
                      className="w-4 h-4 ml-1"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth={2}
                        d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
                      />
                    </svg>
                  </a>
                )}
              </div>
            </div>
          </div>
        </div>
      )}
      <BackToTopButton />
    </div>
  );
}
