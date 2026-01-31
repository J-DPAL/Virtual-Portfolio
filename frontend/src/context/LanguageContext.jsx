import React, { createContext, useState, useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import i18n from '../i18n';

const LanguageContext = createContext();

export const LanguageProvider = ({ children }) => {
  const [language, setLanguage] = useState(
    localStorage.getItem('language') || 'en'
  );

  useEffect(() => {
    i18n.changeLanguage(language);
  }, [language]);

  const switchLanguage = (lang) => {
    setLanguage(lang);
    localStorage.setItem('language', lang);
    i18n.changeLanguage(lang);
  };

  return (
    <LanguageContext.Provider value={{ language, switchLanguage }}>
      {children}
    </LanguageContext.Provider>
  );
};

export const useLanguage = () => {
  const context = useContext(LanguageContext);
  if (!context) {
    throw new Error('useLanguage must be used within LanguageProvider');
  }
  return context;
};

LanguageProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
