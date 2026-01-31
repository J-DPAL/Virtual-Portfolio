import React from 'react';
import { useTranslation } from 'react-i18next';

export default function Footer() {
  const { t } = useTranslation();
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gray-800 text-white mt-12">
      <div className="container mx-auto px-4 py-8 text-center">
        <p>
          &copy; {currentYear} Virtual Portfolio.{' '}
          {t('allRightsReserved', { defaultValue: 'All rights reserved.' })}
        </p>
      </div>
    </footer>
  );
}
