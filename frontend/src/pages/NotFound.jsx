import React from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function NotFound() {
  const { t } = useTranslation();
  return (
    <div className="min-h-[60vh] flex items-center justify-center px-4">
      <div className="text-center space-y-4">
        <p className="text-sm font-semibold text-blue-600">404</p>
        <h1 className="text-3xl font-bold text-slate-900">
          {t('pageNotFound')}
        </h1>
        <p className="text-slate-600">{t('letGetYouBack')}</p>
        <div className="flex items-center justify-center gap-3">
          <Link
            to="/"
            className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition"
          >
            {t('goHome')}
          </Link>
          <Link
            to="/projects"
            className="px-4 py-2 rounded-lg border border-slate-200 text-slate-700 hover:border-blue-200 hover:text-blue-700 transition"
          >
            {t('viewProjects')}
          </Link>
        </div>
      </div>
    </div>
  );
}
