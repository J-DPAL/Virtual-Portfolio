import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useTheme } from './context/ThemeContext';
import Header from './components/common/Header';
import Footer from './components/common/Footer';
import HomePage from './pages/HomePage';
import ProjectsPage from './pages/ProjectsPage';
import SkillsPage from './pages/SkillsPage';
import ExperiencePage from './pages/ExperiencePage';
import EducationPage from './pages/EducationPage';
import HobbiesPage from './pages/HobbiesPage';
import TestimonialsPage from './pages/TestimonialsPage';
import ContactPage from './pages/ContactPage';
import NotFound from './pages/NotFound';
import AdminLogin from './components/admin/AdminLogin';
import AdminDashboard from './components/admin/AdminDashboard';
import SkillsManagement from './components/admin/SkillsManagement';
import ProjectsManagement from './components/admin/ProjectsManagement';
import ExperienceManagement from './components/admin/ExperienceManagement';
import EducationManagement from './components/admin/EducationManagement';
import HobbiesManagement from './components/admin/HobbiesManagement';
import TestimonialsManagement from './components/admin/TestimonialsManagement';
import MessagesView from './components/admin/MessagesView';
import ResumeUpload from './components/admin/ResumeUpload';
import ProtectedRoute from './components/ProtectedRoute';
import { PageTransitionWrapper } from './components/common/PageTransitionWrapper';
import './index.css';

function App() {
  const { isDark } = useTheme();
  const { t } = useTranslation();
  const location = useLocation();

  return (
    <div
      className={`min-h-screen flex flex-col ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950 text-slate-100'
          : 'bg-gradient-to-br from-blue-50 via-slate-50 to-teal-50 text-slate-900'
      }`}
      dir="ltr"
    >
      <a href="#main-content" className="skip-link">
        {t('skipToMainContent')}
      </a>
      <Header />
      <main id="main-content" className="flex-grow">
        <PageTransitionWrapper location={location}>
          <Routes location={location} key={location.pathname}>
            {/* Public Routes */}
            <Route path="/" element={<HomePage />} />
            <Route path="/projects" element={<ProjectsPage />} />
            <Route path="/skills" element={<SkillsPage />} />
            <Route path="/experience" element={<ExperiencePage />} />
            <Route path="/education" element={<EducationPage />} />
            <Route path="/hobbies" element={<HobbiesPage />} />
            <Route path="/testimonials" element={<TestimonialsPage />} />
            <Route path="/contact" element={<ContactPage />} />
            <Route path="/login" element={<AdminLogin />} />

            {/* Protected Admin Routes */}
            <Route
              path="/admin/dashboard"
              element={
                <ProtectedRoute requireAdmin>
                  <AdminDashboard />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/skills"
              element={
                <ProtectedRoute requireAdmin>
                  <SkillsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/projects"
              element={
                <ProtectedRoute requireAdmin>
                  <ProjectsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/experience"
              element={
                <ProtectedRoute requireAdmin>
                  <ExperienceManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/education"
              element={
                <ProtectedRoute requireAdmin>
                  <EducationManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/hobbies"
              element={
                <ProtectedRoute requireAdmin>
                  <HobbiesManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/testimonials"
              element={
                <ProtectedRoute requireAdmin>
                  <TestimonialsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/messages"
              element={
                <ProtectedRoute requireAdmin>
                  <MessagesView />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/files"
              element={
                <ProtectedRoute requireAdmin>
                  <ResumeUpload />
                </ProtectedRoute>
              }
            />

            <Route path="*" element={<NotFound />} />
          </Routes>
        </PageTransitionWrapper>
      </main>
      <Footer />
    </div>
  );
}

export default App;
