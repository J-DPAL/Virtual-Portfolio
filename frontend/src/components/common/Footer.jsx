import React from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../../context/ThemeContext';

export default function Footer() {
  const { t } = useTranslation();
  const { isDark } = useTheme();
  const currentYear = new Date().getFullYear();

  return (
    <footer
      className={`${
        isDark ? 'bg-slate-900 text-slate-100' : 'bg-gray-800 text-white'
      } mt-12 transition-colors duration-200`}
    >
      <div className="container mx-auto px-4 py-8 text-center">
        <p>
          &copy; {currentYear} Virtual Portfolio.{' '}
          {t('allRightsReserved', { defaultValue: 'All rights reserved.' })}
        </p>
      </div>
    </footer>
  );
}
