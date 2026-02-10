import React, { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useLanguage } from '../../context/LanguageContext';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { useTranslation } from 'react-i18next';
import { downloadResume } from '../../services/filesService';

export default function Header() {
  const { language, switchLanguage } = useLanguage();
  const { isAuthenticated, logout } = useAuth();
  const { isDark, toggleTheme } = useTheme();
  const { t, i18n } = useTranslation();
  const navigate = useNavigate();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [downloading, setDownloading] = useState(false);
  const [languageDropdown, setLanguageDropdown] = useState(false);

  const navItems = [
    { label: t('home'), to: '/' },
    { label: t('projects'), to: '/projects' },
    { label: t('skills'), to: '/skills' },
    { label: t('experience'), to: '/experience' },
    { label: t('education'), to: '/education' },
    { label: t('hobbies'), to: '/hobbies' },
    { label: t('testimonials'), to: '/testimonials' },
    { label: t('contact'), to: '/contact' },
  ];

  const handleDownloadResume = async () => {
    try {
      setDownloading(true);
      const response = await downloadResume(language);
      const fileNameByLanguage = {
        en: 'CV_JD_EN.pdf',
        fr: 'CV_JD_FR.pdf',
        es: 'CV_JD_ES.pdf',
      };
      const downloadFileName =
        fileNameByLanguage[language?.toLowerCase?.()] || 'CV_JD_EN.pdf';
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', downloadFileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error('Error downloading resume:', error);
      alert('Resume download is not available at the moment');
    } finally {
      setDownloading(false);
    }
  };

  const handleLogout = () => {
    logout();
    setMobileMenuOpen(false);
    navigate('/login');
  };

  return (
    <header
      className={`${
        isDark
          ? 'bg-slate-900/95 border-slate-700 backdrop-blur-lg'
          : 'bg-gradient-to-r from-slate-50/95 via-blue-50/90 to-slate-50/95 border-slate-200 backdrop-blur-lg'
      } border-b sticky top-0 z-50 shadow-sm transition-colors duration-200`}
    >
      <nav className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-10 h-10 bg-gradient-to-br from-blue-600 to-cyan-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-xl">DP</span>
            </div>
            <span
              className={`text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600`}
            >
              Digital Portfolio
            </span>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden lg:flex items-center gap-1">
            {navItems.map((item) => (
              <NavLink
                key={item.to}
                to={item.to}
                className={({ isActive }) =>
                  `px-4 py-2 rounded-lg text-sm font-medium transition-all ${
                    isActive
                      ? 'bg-gradient-to-r from-blue-600 to-cyan-600 text-white shadow-md'
                      : isDark
                        ? 'text-slate-300 hover:bg-slate-800'
                        : 'text-slate-700 hover:bg-slate-100'
                  }`
                }
              >
                {item.label}
              </NavLink>
            ))}
          </div>

          {/* Actions */}
          <div className="flex items-center gap-2">
            <button
              onClick={handleDownloadResume}
              disabled={downloading}
              className="hidden md:flex items-center px-4 py-2 text-sm font-medium rounded-lg bg-gradient-to-r from-emerald-600 to-teal-600 text-white hover:shadow-lg transition-all disabled:opacity-50"
              title={t('downloadResume')}
            >
              <svg
                className="w-4 h-4 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
              {downloading ? 'Downloading...' : 'CV'}
            </button>

            {/* Theme Toggle Button */}
            <button
              onClick={toggleTheme}
              className={`px-3 py-2 text-sm font-medium rounded-lg border-2 transition-all flex items-center gap-2 ${
                isDark
                  ? 'border-slate-600 hover:border-yellow-500 hover:text-yellow-400 text-slate-300'
                  : 'border-slate-200 hover:border-blue-500 hover:text-blue-600 text-slate-700'
              }`}
              title={isDark ? 'Switch to light mode' : 'Switch to dark mode'}
            >
              {isDark ? (
                <svg
                  className="w-4 h-4"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path d="M12 3v1m0 16v1m9-9h-1m-16 0H1m15.657 5.657l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
              ) : (
                <svg
                  className="w-4 h-4"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
                </svg>
              )}
            </button>

            <div className="relative group">
              <button
                onClick={() => setLanguageDropdown(!languageDropdown)}
                className={`px-3 py-2 text-sm font-medium rounded-lg border-2 transition-all flex items-center gap-2 ${
                  isDark
                    ? 'border-slate-600 hover:border-blue-500 hover:text-blue-400 text-slate-300'
                    : 'border-slate-200 hover:border-blue-500 hover:text-blue-600 text-slate-700'
                }`}
                title={t('language')}
              >
                {language.toUpperCase()}
              </button>

              {languageDropdown && (
                <div
                  className={`absolute right-0 mt-2 w-32 ${
                    isDark
                      ? 'bg-slate-800 border-slate-700'
                      : 'bg-slate-50/95 border-slate-200'
                  } border rounded-lg shadow-lg z-50`}
                >
                  <button
                    onClick={() => {
                      switchLanguage('en');
                      i18n.changeLanguage('en');
                      setLanguageDropdown(false);
                    }}
                    className={`w-full text-left px-4 py-2 text-sm font-medium rounded-t-lg flex items-center gap-2 transition-colors ${
                      isDark
                        ? 'hover:bg-slate-700 text-slate-200'
                        : 'hover:bg-slate-100 text-slate-700'
                    }`}
                  >
                    🇬🇧 English
                  </button>
                  <button
                    onClick={() => {
                      switchLanguage('fr');
                      i18n.changeLanguage('fr');
                      setLanguageDropdown(false);
                    }}
                    className={`w-full text-left px-4 py-2 text-sm font-medium flex items-center gap-2 transition-colors ${
                      isDark
                        ? 'hover:bg-slate-700 text-slate-200'
                        : 'hover:bg-slate-100 text-slate-700'
                    }`}
                  >
                    🇫🇷 Français
                  </button>
                  <button
                    onClick={() => {
                      switchLanguage('es');
                      i18n.changeLanguage('es');
                      setLanguageDropdown(false);
                    }}
                    className={`w-full text-left px-4 py-2 text-sm font-medium rounded-b-lg flex items-center gap-2 transition-colors ${
                      isDark
                        ? 'hover:bg-slate-700 text-slate-200'
                        : 'hover:bg-slate-100 text-slate-700'
                    }`}
                  >
                    🇪🇸 Español
                  </button>
                </div>
              )}
            </div>

            {isAuthenticated ? (
              <>
                <Link
                  to="/admin/dashboard"
                  className={`hidden md:flex items-center px-4 py-2 text-sm font-medium rounded-lg transition ${
                    isDark
                      ? 'bg-slate-700 text-white hover:bg-slate-600'
                      : 'bg-slate-700 text-white hover:bg-slate-800'
                  }`}
                >
                  <svg
                    className="w-4 h-4 mr-2"
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
                  Admin
                </Link>
                <button
                  onClick={handleLogout}
                  className="px-4 py-2 text-sm font-medium rounded-lg bg-red-600 text-white hover:bg-red-700 transition"
                >
                  {t('logout')}
                </button>
              </>
            ) : (
              <Link
                to="/login"
                className="px-4 py-2 text-sm font-medium rounded-lg bg-gradient-to-r from-blue-600 to-cyan-600 text-white hover:shadow-lg transition-all"
              >
                {t('login')}
              </Link>
            )}

            {/* Mobile Menu Button */}
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className={`lg:hidden p-2 rounded-lg transition ${
                isDark
                  ? 'hover:bg-slate-800 text-slate-300'
                  : 'hover:bg-slate-100 text-slate-900'
              }`}
            >
              <svg
                className="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                {mobileMenuOpen ? (
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                ) : (
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M4 6h16M4 12h16M4 18h16"
                  />
                )}
              </svg>
            </button>
          </div>
        </div>

        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <div
            className={`lg:hidden mt-4 py-4 ${
              isDark ? 'border-slate-700' : 'border-slate-200'
            } border-t transition-colors`}
          >
            <div className="flex flex-col space-y-2">
              {navItems.map((item) => (
                <NavLink
                  key={item.to}
                  to={item.to}
                  onClick={() => setMobileMenuOpen(false)}
                  className={({ isActive }) =>
                    `px-4 py-3 rounded-lg text-sm font-medium transition ${
                      isActive
                        ? 'bg-gradient-to-r from-blue-600 to-cyan-600 text-white'
                        : isDark
                          ? 'text-slate-300 hover:bg-slate-800'
                          : 'text-slate-700 hover:bg-slate-100'
                    }`
                  }
                >
                  {item.label}
                </NavLink>
              ))}
              <button
                onClick={() => {
                  handleDownloadResume();
                  setMobileMenuOpen(false);
                }}
                disabled={downloading}
                className={`px-4 py-3 text-sm font-medium rounded-lg text-white text-left transition ${
                  isDark
                    ? 'bg-emerald-700 hover:bg-emerald-600'
                    : 'bg-emerald-600 hover:bg-emerald-700'
                }`}
              >
                📄 {t('downloadResume')}
              </button>
            </div>
          </div>
        )}
      </nav>
    </header>
  );
}
