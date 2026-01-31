import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { getAllProjects } from '../services/projectsService';

export default function ProjectsPage() {
  const { t, i18n } = useTranslation();
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchProjects();
  }, []);

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

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 via-white to-teal-50">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
          <p className="mt-4 text-slate-600">{t('loading')}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-teal-50 py-12">
      <div className="container mx-auto px-4">
        <header className="text-center mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-teal-600 mb-4">
            {t('projects')}
          </h1>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto mb-6">
            {t('explorePortfolioProjects')}
          </p>
          <Link
            to="/contact"
            className="inline-flex items-center px-6 py-3 rounded-xl bg-gradient-to-r from-blue-600 to-teal-600 text-white font-medium hover:shadow-lg transform hover:-translate-y-0.5 transition-all duration-200"
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
          <div className="max-w-2xl mx-auto mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
            {t('errorOccurred')}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-7xl mx-auto">
          {projects.map((project) => (
            <div
              key={project.id}
              className="group bg-white rounded-3xl shadow-lg border border-slate-200 overflow-hidden hover:-translate-y-2 hover:shadow-2xl transition-all duration-300"
            >
              <div className="bg-gradient-to-br from-blue-500 to-teal-600 h-48 flex items-center justify-center">
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
                    d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4"
                  />
                </svg>
              </div>
              <div className="p-6">
                <h2 className="text-2xl font-bold text-slate-900 mb-3 group-hover:text-indigo-600 transition">
                  {currentLang === 'fr' && project.titleFr
                    ? project.titleFr
                    : project.titleEn}
                </h2>
                <p className="text-slate-600 mb-4 line-clamp-3">
                  {currentLang === 'fr' && project.descriptionFr
                    ? project.descriptionFr
                    : project.descriptionEn}
                </p>
                {project.technologies && (
                  <div className="flex flex-wrap gap-2 mb-4">
                    {project.technologies
                      .split(',')
                      .slice(0, 3)
                      .map((tech, idx) => (
                        <span
                          key={idx}
                          className="text-xs px-3 py-1 rounded-full bg-indigo-50 text-indigo-700 border border-indigo-200 font-medium"
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
                    className="inline-flex items-center text-indigo-600 hover:text-indigo-700 font-medium transition"
                  >
                    View Project
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

        {projects.length === 0 && !loading && (
          <div className="text-center py-12">
            <p className="text-slate-600 text-lg">{t('noProjectsAvailable')}</p>
          </div>
        )}
      </div>
    </div>
  );
}
