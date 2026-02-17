import React, { createContext, useState, useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import {
  getCurrentUser,
  logoutUser,
} from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isAuthLoading, setIsAuthLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        const response = await getCurrentUser();
        setUser(response.data);
        setIsAuthenticated(true);
      } catch (err) {
        if (err?.response?.status === 403) {
          // Not authenticated, prompt login
          setUser(null);
          setIsAuthenticated(false);
        } else {
          // Other errors
          setUser(null);
          setIsAuthenticated(false);
        }
      } finally {
        setIsAuthLoading(false);
      }
    };

    initializeAuth();
  }, []);

  const login = (userData) => {
    setUser(userData);
    setIsAuthenticated(true);
  };

  const logout = async () => {
    try {
      await logoutUser();
    } finally {
      setUser(null);
      setIsAuthenticated(false);
    }
  };

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, isAuthLoading, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
