import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../context/ThemeContext';

export default function HomePage() {
  const { t } = useTranslation();
  const { isDark } = useTheme();
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    setIsVisible(true);
  }, []);

  const features = [
    {
      icon: 'M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4',
      title: t('projects'),
      desc: t('showcaseWork'),
      to: '/projects',
      gradient: 'from-blue-500 to-cyan-500',
    },
    {
      icon: 'M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z',
      title: t('skills'),
      desc: t('displayExpertise'),
      to: '/skills',
      gradient: 'from-blue-500 to-cyan-500',
    },
    {
      icon: 'M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
      title: t('experience'),
      desc: t('shareProfessionalJourney'),
      to: '/experience',
      gradient: 'from-orange-500 to-red-500',
    },
    {
      icon: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
      title: t('contact'),
      desc: t('collaborateOnProject'),
      to: '/contact',
      gradient: 'from-green-500 to-teal-500',
    },
  ];

  const stats = [
    { label: t('projectsCompleted'), value: '50+', icon: '🚀' },
    { label: t('technologiesMastered'), value: '20+', icon: '💻' },
    { label: t('yearsExperience'), value: '5+', icon: '⏱️' },
    { label: t('clientSatisfaction'), value: '100%', icon: '⭐' },
  ];

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
      ? `repeating-linear-gradient(120deg, rgba(45, 212, 191, 0.2) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(59, 130, 246, 0.18) 0 1px, transparent 1px 18px)`
      : `repeating-linear-gradient(120deg, rgba(37, 99, 235, 0.2) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(14, 165, 233, 0.18) 0 1px, transparent 1px 18px)`,
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

  const waveFill = isDark ? 'rgb(15, 23, 42)' : 'rgb(248, 250, 252)';

  return (
    <div
      className={`min-h-screen transition-colors duration-200 relative overflow-hidden ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-blue-950 to-slate-950'
          : 'bg-gradient-to-br from-slate-900 via-blue-900 to-slate-900'
      }`}
    >
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
      {/* Hero Section with Animated Gradient */}
      <section className="relative overflow-hidden z-10">
        {/* Animated Background Elements */}
        <div className="absolute inset-0 overflow-hidden">
          <div className="absolute -top-40 -right-40 w-80 h-80 bg-blue-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob"></div>
          <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-cyan-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob animation-delay-2000"></div>
          <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-80 h-80 bg-green-500 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob animation-delay-4000"></div>
        </div>

        <div
          className={`relative container mx-auto px-4 pt-20 pb-32 transition-all duration-1000 ${isVisible ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-10'}`}
        >
          <div className="max-w-4xl mx-auto text-center">
            {/* Badge */}
            <div className="inline-flex items-center px-4 py-2 bg-white bg-opacity-10 backdrop-blur-md rounded-full border border-white border-opacity-20 mb-8">
              <span className="w-2 h-2 bg-green-400 rounded-full mr-2 animate-pulse"></span>
              <span className="text-white text-sm font-medium">
                {t('availableForOpportunities')}
              </span>
            </div>

            {/* Main Heading */}
            <h1 className="text-5xl md:text-7xl font-bold mb-6 leading-tight">
              <span className="bg-clip-text text-transparent bg-gradient-to-r from-cyan-400 via-blue-400 to-green-400 animate-gradient">
                {t('welcome')}
              </span>
            </h1>

            <p className="text-xl md:text-2xl text-slate-300 mb-8 max-w-3xl mx-auto leading-relaxed">
              {t('fullStackDeveloper')}
            </p>

            {/* CTA Buttons */}
            <div className="flex flex-col sm:flex-row gap-4 justify-center items-center mb-12">
              <Link
                to="/projects"
                className="group relative px-8 py-4 bg-gradient-to-r from-cyan-500 to-blue-600 rounded-2xl font-semibold text-white overflow-hidden transition-all hover:shadow-2xl hover:shadow-cyan-500/50 hover:-translate-y-1"
              >
                <span className="relative z-10 flex items-center gap-2">
                  {t('viewMyWork')}
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
                      d="M13 7l5 5m0 0l-5 5m5-5H6"
                    />
                  </svg>
                </span>
                <div className="absolute inset-0 bg-gradient-to-r from-blue-600 to-cyan-500 opacity-0 group-hover:opacity-100 transition-opacity"></div>
              </Link>

              <Link
                to="/contact"
                className="px-8 py-4 bg-white bg-opacity-10 backdrop-blur-md border-2 border-white border-opacity-20 rounded-2xl font-semibold text-white hover:bg-opacity-20 transition-all hover:-translate-y-1"
              >
                {t('getInTouch')}
              </Link>
            </div>

            {/* Floating Tech Stack Pills */}
            <div className="flex flex-wrap gap-3 justify-center">
              {['React', 'Spring Boot', 'PostgreSQL', 'Docker', 'AWS'].map(
                (tech, idx) => (
                  <div
                    key={tech}
                    className="px-4 py-2 bg-white bg-opacity-5 backdrop-blur-sm border border-white border-opacity-10 rounded-full text-slate-300 text-sm hover:bg-opacity-10 transition-all cursor-default"
                    style={{ animationDelay: `${idx * 100}ms` }}
                  >
                    {tech}
                  </div>
                )
              )}
            </div>
          </div>
        </div>

        {/* Wave Divider */}
        <div className="absolute bottom-0 left-0 right-0">
          <svg
            viewBox="0 0 1440 120"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M0 0L60 10C120 20 240 40 360 46.7C480 53 600 47 720 43.3C840 40 960 40 1080 46.7C1200 53 1320 67 1380 73.3L1440 80V120H1380C1320 120 1200 120 1080 120C960 120 840 120 720 120C600 120 480 120 360 120C240 120 120 120 60 120H0V0Z"
              fill={waveFill}
            />
          </svg>
        </div>
      </section>

      {/* Stats Section */}
      <section
        className={`py-20 transition-colors duration-200 ${
          isDark ? 'bg-slate-900' : 'bg-slate-50'
        }`}
      >
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {stats.map((stat, idx) => (
              <div
                key={stat.label}
                className="text-center transform hover:scale-105 transition-transform"
                style={{ animationDelay: `${idx * 150}ms` }}
              >
                <div className="text-5xl mb-3">{stat.icon}</div>
                <div className="text-4xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600 mb-2">
                  {stat.value}
                </div>
                <div
                  className={`font-medium ${
                    isDark ? 'text-slate-400' : 'text-slate-600'
                  }`}
                >
                  {stat.label}
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Features Grid */}
      <section
        className={`py-20 transition-colors duration-200 ${
          isDark ? 'bg-slate-900' : 'bg-slate-50'
        }`}
      >
        <div className="container mx-auto px-4">
          <div className="text-center mb-16">
            <h2
              className={`text-4xl md:text-5xl font-bold mb-4 ${
                isDark ? 'text-slate-100' : 'text-slate-900'
              }`}
            >
              {t('exploreMyPortfolio')}
            </h2>
            <p
              className={`text-xl max-w-2xl mx-auto ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('discoverProjectsSkillsJourney')}
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 max-w-7xl mx-auto">
            {features.map((feature, idx) => (
              <Link
                key={feature.title}
                to={feature.to}
                className={`group relative rounded-3xl p-8 shadow-lg hover:shadow-2xl transition-all duration-300 hover:-translate-y-2 overflow-hidden ${
                  isDark
                    ? 'bg-slate-800 hover:shadow-slate-900'
                    : 'bg-slate-50 hover:shadow-slate-300'
                }`}
                style={{ animationDelay: `${idx * 100}ms` }}
              >
                {/* Gradient Overlay on Hover */}
                <div
                  className={`absolute inset-0 bg-gradient-to-br ${feature.gradient} opacity-0 group-hover:opacity-10 transition-opacity`}
                ></div>

                {/* Icon */}
                <div
                  className={`relative w-16 h-16 bg-gradient-to-br ${feature.gradient} rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 group-hover:rotate-3 transition-transform`}
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
                      d={feature.icon}
                    />
                  </svg>
                </div>

                {/* Content */}
                <h3
                  className={`relative text-2xl font-bold mb-3 group-hover:text-transparent group-hover:bg-clip-text group-hover:bg-gradient-to-r group-hover:from-blue-600 group-hover:to-cyan-600 transition-all ${
                    isDark ? 'text-slate-100' : 'text-slate-900'
                  }`}
                >
                  {feature.title}
                </h3>
                <p
                  className={`relative mb-4 ${
                    isDark ? 'text-slate-400' : 'text-slate-600'
                  }`}
                >
                  {feature.desc}
                </p>

                {/* Arrow */}
                <div
                  className={`relative inline-flex items-center text-sm font-semibold bg-gradient-to-r ${feature.gradient} bg-clip-text text-transparent`}
                >
                  {t('explore')}
                  <svg
                    className="w-4 h-4 ml-2 group-hover:translate-x-2 transition-transform"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9 5l7 7-7 7"
                    />
                  </svg>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section
        className={`py-20 transition-colors duration-200 ${
          isDark
            ? 'bg-gradient-to-br from-blue-950 via-slate-950 to-cyan-950'
            : 'bg-gradient-to-br from-blue-900 via-slate-900 to-cyan-900'
        }`}
      >
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-4xl md:text-5xl font-bold text-white mb-6">
            {t('readyToStartProject')}
          </h2>
          <p className="text-xl text-slate-300 mb-10 max-w-2xl mx-auto">
            {t('collaborateBringIdeas')}
          </p>
          <Link
            to="/contact"
            className="inline-flex items-center gap-2 px-10 py-5 bg-gradient-to-r from-cyan-500 to-blue-600 rounded-2xl font-bold text-white text-lg hover:shadow-2xl hover:shadow-cyan-500/50 hover:-translate-y-1 transition-all"
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
                d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
              />
            </svg>
            {t('letsTalk')}
          </Link>
        </div>
      </section>

      <style>{`
        @keyframes blob {
          0%, 100% {
            transform: translate(0px, 0px) scale(1);
          }
          33% {
            transform: translate(30px, -50px) scale(1.1);
          }
          66% {
            transform: translate(-20px, 20px) scale(0.9);
          }
        }
        
        .animate-blob {
          animation: blob 7s infinite;
        }
        
        .animation-delay-2000 {
          animation-delay: 2s;
        }
        
        .animation-delay-4000 {
          animation-delay: 4s;
        }

        @keyframes gradient {
          0%, 100% {
            background-position: 0% 50%;
          }
          50% {
            background-position: 100% 50%;
          }
        }

        .animate-gradient {
          background-size: 200% 200%;
          animation: gradient 3s ease infinite;
        }
      `}</style>
    </div>
  );
}
