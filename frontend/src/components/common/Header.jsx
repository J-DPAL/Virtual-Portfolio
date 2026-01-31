import React, { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { useLanguage } from '../../context/LanguageContext';
import { useAuth } from '../../context/AuthContext';
import { useTranslation } from 'react-i18next';
import { downloadResume } from '../../services/filesService';

export default function Header() {
  const { language, switchLanguage } = useLanguage();
  const { isAuthenticated, logout } = useAuth();
  const { t, i18n } = useTranslation();
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

  return (
    <header className="bg-white/95 backdrop-blur-lg border-b border-slate-200 sticky top-0 z-50 shadow-sm">
      <nav className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-10 h-10 bg-gradient-to-br from-blue-600 to-cyan-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-xl">VP</span>
            </div>
            <span className="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600">
              Virtual Portfolio
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

            <div className="relative group">
              <button
                onClick={() => setLanguageDropdown(!languageDropdown)}
                className="px-3 py-2 text-sm font-medium rounded-lg border-2 border-slate-200 hover:border-blue-500 hover:text-blue-600 transition-all flex items-center gap-2"
                title={t('language')}
              >
                <svg
                  className="w-4 h-4"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"
                  />
                </svg>
                {language.toUpperCase()}
              </button>

              {languageDropdown && (
                <div className="absolute right-0 mt-2 w-32 bg-white border border-slate-200 rounded-lg shadow-lg z-50">
                  <button
                    onClick={() => {
                      switchLanguage('en');
                      i18n.changeLanguage('en');
                      setLanguageDropdown(false);
                    }}
                    className="w-full text-left px-4 py-2 hover:bg-slate-100 text-sm font-medium rounded-t-lg flex items-center gap-2"
                  >
                    🇬🇧 English
                  </button>
                  <button
                    onClick={() => {
                      switchLanguage('fr');
                      i18n.changeLanguage('fr');
                      setLanguageDropdown(false);
                    }}
                    className="w-full text-left px-4 py-2 hover:bg-slate-100 text-sm font-medium flex items-center gap-2"
                  >
                    🇫🇷 Français
                  </button>
                  <button
                    onClick={() => {
                      switchLanguage('es');
                      i18n.changeLanguage('es');
                      setLanguageDropdown(false);
                    }}
                    className="w-full text-left px-4 py-2 hover:bg-slate-100 text-sm font-medium rounded-b-lg flex items-center gap-2"
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
                  className="hidden md:flex items-center px-4 py-2 text-sm font-medium rounded-lg bg-slate-700 text-white hover:bg-slate-800 transition"
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
                  onClick={logout}
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
              className="lg:hidden p-2 rounded-lg hover:bg-slate-100 transition"
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
          <div className="lg:hidden mt-4 py-4 border-t border-slate-200">
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
                className="px-4 py-3 text-sm font-medium rounded-lg bg-emerald-600 text-white hover:bg-emerald-700 transition text-left"
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
