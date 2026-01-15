import { useLanguage } from '../context/LanguageContext';
import { useAuth } from '../context/AuthContext';
import { useTranslation } from 'react-i18next';

export default function Header() {
  const { language, switchLanguage } = useLanguage();
  const { isAuthenticated, logout } = useAuth();
  const { t } = useTranslation();

  return (
    <header className="bg-white shadow">
      <nav className="container mx-auto px-4 py-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold">{t('welcome')}</h1>
        
        <div className="flex items-center gap-4">
          <button
            onClick={() => switchLanguage(language === 'en' ? 'fr' : 'en')}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {language === 'en' ? 'FR' : 'EN'}
          </button>

          {isAuthenticated && (
            <button
              onClick={logout}
              className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
            >
              {t('logout')}
            </button>
          )}
        </div>
      </nav>
    </header>
  );
}
