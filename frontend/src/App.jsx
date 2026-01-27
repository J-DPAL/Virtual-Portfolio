import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
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
import './index.css';

function App() {
  const { i18n } = useTranslation();

  return (
    <Router>
      <div
        className="min-h-screen flex flex-col bg-gradient-to-br from-slate-50 via-white to-slate-50 text-slate-900"
        dir={i18n.language === 'ar' ? 'rtl' : 'ltr'}
      >
        <Header />
        <main className="flex-grow">
          <Routes>
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
                <ProtectedRoute>
                  <AdminDashboard />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/skills"
              element={
                <ProtectedRoute>
                  <SkillsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/projects"
              element={
                <ProtectedRoute>
                  <ProjectsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/experience"
              element={
                <ProtectedRoute>
                  <ExperienceManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/education"
              element={
                <ProtectedRoute>
                  <EducationManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/hobbies"
              element={
                <ProtectedRoute>
                  <HobbiesManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/testimonials"
              element={
                <ProtectedRoute>
                  <TestimonialsManagement />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/messages"
              element={
                <ProtectedRoute>
                  <MessagesView />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin/files"
              element={
                <ProtectedRoute>
                  <ResumeUpload />
                </ProtectedRoute>
              }
            />

            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
