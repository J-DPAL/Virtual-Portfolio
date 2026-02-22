import React, { createContext, useState, useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import {
  getCurrentUser,
  logoutUser,
} from '../services/authService';
import { isSupabaseConfigured, supabase } from '../services/supabaseClient';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [isAuthLoading, setIsAuthLoading] = useState(true);

  const adminEmails = (import.meta.env.VITE_ADMIN_EMAILS || '')
    .split(',')
    .map((s) => s.trim().toLowerCase())
    .filter(Boolean);

  const computeIsAdminByEmail = (u) => {
    if (!u?.email) return false;
    if (adminEmails.length === 0) return false;
    return adminEmails.includes(u.email.toLowerCase());
  };

  const resolveIsAdmin = async (u) => {
    if (!u) return false;
    if (typeof u.role === 'string' && u.role.toUpperCase() === 'ADMIN') {
      return true;
    }

    // Preferred check in Supabase mode: RLS-backed admin function.
    if (isSupabaseConfigured()) {
      try {
        const { data, error } = await supabase.rpc('is_admin');
        if (!error && typeof data === 'boolean') {
          return data || computeIsAdminByEmail(u);
        }
      } catch {
        // Fall back to email-based check below.
      }
    }

    return computeIsAdminByEmail(u);
  };

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        if (isSupabaseConfigured()) {
          const { data } = await supabase.auth.getSession();
          const sessionUser = data?.session?.user ?? null;
          const adminState = await resolveIsAdmin(sessionUser);
          setUser(sessionUser);
          setIsAuthenticated(Boolean(sessionUser));
          setIsAdmin(adminState);
          return;
        }

        const response = await getCurrentUser();
        const adminState = await resolveIsAdmin(response.data);
        setUser(response.data);
        setIsAuthenticated(true);
        setIsAdmin(adminState);
      } catch (err) {
        if (err?.response?.status === 403) {
          // Not authenticated, prompt login
          setUser(null);
          setIsAuthenticated(false);
          setIsAdmin(false);
        } else {
          // Other errors
          setUser(null);
          setIsAuthenticated(false);
          setIsAdmin(false);
        }
      } finally {
        setIsAuthLoading(false);
      }
    };

    initializeAuth();

    // Keep auth state synced with Supabase.
    if (isSupabaseConfigured()) {
      const { data: sub } = supabase.auth.onAuthStateChange(
        async (_event, session) => {
          const sessionUser = session?.user ?? null;
          const adminState = await resolveIsAdmin(sessionUser);
          setUser(sessionUser);
          setIsAuthenticated(Boolean(sessionUser));
          setIsAdmin(adminState);
          setIsAuthLoading(false);
        }
      );

      return () => {
        sub?.subscription?.unsubscribe();
      };
    }
  }, []);

  const login = async (userData) => {
    const adminState = await resolveIsAdmin(userData);
    setUser(userData);
    setIsAuthenticated(true);
    setIsAdmin(adminState);
  };

  const logout = async () => {
    try {
      await logoutUser();
    } finally {
      setUser(null);
      setIsAuthenticated(false);
      setIsAdmin(false);
    }
  };

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, isAdmin, isAuthLoading, login, logout }}
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
