import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useTranslation } from 'react-i18next';
import { getAllSkills } from '../../services/skillsService';
import { getAllProjects } from '../../services/projectsService';
import { getAllExperiences } from '../../services/experienceService';
import { getAllEducation } from '../../services/educationService';
import { getAllHobbies } from '../../services/hobbiesService';
import { getAllTestimonials } from '../../services/testimonialsService';
import { getAllMessages } from '../../services/messagesService';

export default function AdminDashboard() {
  const { t } = useTranslation();
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [stats, setStats] = useState({
    skills: 0,
    projects: 0,
    experiences: 0,
    education: 0,
    hobbies: 0,
    testimonials: 0,
    messages: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    fetchStats();
  }, [isAuthenticated, navigate]);

  const fetchStats = async () => {
    try {
      setLoading(true);
      const [
        skills,
        projects,
        experiences,
        education,
        hobbies,
        testimonials,
        messages,
      ] = await Promise.all([
        getAllSkills().catch(() => ({ data: [] })),
        getAllProjects().catch(() => ({ data: [] })),
        getAllExperiences().catch(() => ({ data: [] })),
        getAllEducation().catch(() => ({ data: [] })),
        getAllHobbies().catch(() => ({ data: [] })),
        getAllTestimonials().catch(() => ({ data: [] })),
        getAllMessages().catch(() => ({ data: [] })),
      ]);

      setStats({
        skills: skills.data.length,
        projects: projects.data.length,
        experiences: experiences.data.length,
        education: education.data.length,
        hobbies: hobbies.data.length,
        testimonials: testimonials.data.length,
        messages: messages.data.length,
      });
    } catch (error) {
      console.error('Error fetching stats:', error);
    } finally {
      setLoading(false);
    }
  };

  const sections = [
    {
      title: t('manageSkills'),
      icon: 'M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z',
      count: stats.skills,
      gradient: 'from-blue-500 to-cyan-600',
      link: '/admin/skills',
    },
    {
      title: t('manageProjects'),
      icon: 'M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4',
      count: stats.projects,
      gradient: 'from-blue-600 to-teal-600',
      link: '/admin/projects',
    },
    {
      title: t('manageExperience'),
      icon: 'M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
      count: stats.experiences,
      gradient: 'from-cyan-500 to-teal-600',
      link: '/admin/experience',
    },
    {
      title: t('manageEducation'),
      icon: 'M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253',
      count: stats.education,
      gradient: 'from-emerald-500 to-green-600',
      link: '/admin/education',
    },
    {
      title: t('manageHobbies'),
      icon: 'M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z',
      count: stats.hobbies,
      gradient: 'from-amber-500 to-orange-600',
      link: '/admin/hobbies',
    },
    {
      title: t('manageTestimonials'),
      icon: 'M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z',
      count: stats.testimonials,
      gradient: 'from-yellow-600 to-orange-600',
      link: '/admin/testimonials',
    },
    {
      title: t('viewMessages'),
      icon: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
      count: stats.messages,
      gradient: 'from-blue-600 to-cyan-600',
      link: '/admin/messages',
    },
    {
      title: t('uploadResume'),
      icon: 'M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12',
      count: null,
      gradient: 'from-green-500 to-emerald-600',
      link: '/admin/files',
    },
  ];

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
          <p className="mt-4 text-slate-600">{t('loading')}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-100 via-white to-slate-100 py-12">
      <div className="container mx-auto px-4">
        <div className="mb-12">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-500 mb-4">
            {t('adminDashboard')}
          </h1>
          <p className="text-lg text-slate-600">
            Manage all aspects of your portfolio from this central dashboard
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {sections.map((section) => (
            <Link
              key={section.title}
              to={section.link}
              className="group relative bg-white rounded-2xl shadow-lg border border-slate-200 p-8 hover:-translate-y-2 hover:shadow-2xl transition-all duration-300 overflow-hidden"
            >
              <div
                className={`absolute top-0 right-0 w-24 h-24 bg-gradient-to-br ${section.gradient} opacity-10 rounded-full -mr-12 -mt-12 group-hover:scale-150 transition-transform duration-500`}
              ></div>

              <div
                className={`w-16 h-16 bg-gradient-to-br ${section.gradient} rounded-xl flex items-center justify-center mb-4 group-hover:scale-110 transition-transform duration-300`}
              >
                <svg
                  className="w-8 h-8 text-white"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d={section.icon}
                  />
                </svg>
              </div>

              <h2 className="text-xl font-bold text-slate-900 mb-2">
                {section.title}
              </h2>

              {section.count !== null && (
                <div
                  className={`inline-flex items-center px-3 py-1 rounded-full bg-gradient-to-r ${section.gradient} text-white font-semibold text-sm mb-4`}
                >
                  {section.count} {section.count === 1 ? 'item' : 'items'}
                </div>
              )}
              <div className="flex items-center text-blue-600 font-semibold group-hover:gap-2 transition-all mt-4">
                <span>Manage</span>
                <svg
                  className="w-5 h-5 group-hover:translate-x-1 transition-transform"
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
              </div>
            </Link>
          ))}
        </div>

        <div className="mt-12 bg-white rounded-2xl shadow-lg border border-slate-200 p-8">
          <h2 className="text-2xl font-bold text-slate-900 mb-4">
            Quick Actions
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <Link
              to="/"
              className="flex items-center justify-center px-6 py-4 rounded-xl border-2 border-slate-300 text-slate-700 font-semibold hover:border-blue-500 hover:text-blue-600 transition"
            >
              <svg
                className="w-5 h-5 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                />
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                />
              </svg>
              View Public Site
            </Link>
            <button
              onClick={fetchStats}
              className="flex items-center justify-center px-6 py-4 rounded-xl bg-blue-600 text-white font-semibold hover:bg-blue-700 transition"
            >
              <svg
                className="w-5 h-5 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                />
              </svg>
              Refresh Stats
            </button>
            <div className="flex items-center justify-center px-6 py-4 rounded-xl border-2 border-slate-300 text-slate-500 font-semibold cursor-not-allowed opacity-50">
              <svg
                className="w-5 h-5 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"
                />
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                />
              </svg>
              Settings (Coming Soon)
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
