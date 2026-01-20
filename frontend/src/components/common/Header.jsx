import { Link, NavLink } from 'react-router-dom';
import { useLanguage } from '../../context/LanguageContext';
import { useAuth } from '../../context/AuthContext';
import { useTranslation } from 'react-i18next';

export default function Header() {
  const { language, switchLanguage } = useLanguage();
  const { isAuthenticated, logout } = useAuth();
  const { t } = useTranslation();

  const navItems = [
    { label: t('home'), to: '/' },
    { label: t('projects'), to: '/projects' },
    { label: t('skills'), to: '/skills' },
    { label: t('experience'), to: '/experience' },
    { label: t('contact'), to: '/contact' },
  ];

  return (
    <header className="bg-white/80 backdrop-blur border-b border-slate-200 sticky top-0 z-20">
      <nav className="container mx-auto px-4 py-4 flex items-center justify-between gap-4">
        <Link to="/" className="text-2xl font-bold text-slate-900">Virtual Portfolio</Link>

        <div className="hidden md:flex items-center gap-4 text-sm font-medium text-slate-700">
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                `px-3 py-2 rounded-lg transition ${isActive ? 'bg-blue-50 text-blue-700' : 'hover:bg-slate-100'}`
              }
            >
              {item.label}
            </NavLink>
          ))}
        </div>

        <div className="flex items-center gap-2">
          <button
            onClick={() => switchLanguage(language === 'en' ? 'fr' : 'en')}
            className="px-3 py-2 text-sm rounded-lg border border-slate-200 hover:border-blue-200 hover:text-blue-700"
          >
            {language === 'en' ? 'FR' : 'EN'}
          </button>

          {isAuthenticated ? (
            <button
              onClick={logout}
              className="px-3 py-2 text-sm rounded-lg bg-red-600 text-white hover:bg-red-700"
            >
              {t('logout')}
            </button>
          ) : (
            <Link
              to="/login"
              className="px-3 py-2 text-sm rounded-lg bg-blue-600 text-white hover:bg-blue-700"
            >
              {t('login')}
            </Link>
          )}
        </div>
      </nav>
    </header>
  );
}
