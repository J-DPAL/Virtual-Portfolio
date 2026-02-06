import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useTheme } from '../context/ThemeContext';
import { getAllSkills } from '../services/skillsService';

export default function SkillsPage() {
  const { t, i18n } = useTranslation();
  const { isDark } = useTheme();
  const [skills, setSkills] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const currentLang = i18n.language;

  useEffect(() => {
    fetchSkills();
  }, []);

  const fetchSkills = async () => {
    try {
      setLoading(true);
      const response = await getAllSkills();
      setSkills(response.data);
    } catch (err) {
      setError(t('loadSkillsFailed'));
      console.error('Error fetching skills:', err);
    } finally {
      setLoading(false);
    }
  };

  const getLevelColor = (level) => {
    const levels = isDark
      ? {
          beginner: 'bg-green-900/30 text-green-300 border-green-700',
          intermediate: 'bg-blue-900/30 text-blue-300 border-blue-700',
          advanced: 'bg-teal-900/30 text-teal-300 border-teal-700',
          expert: 'bg-orange-900/30 text-orange-300 border-orange-700',
        }
      : {
          beginner: 'bg-green-50 text-green-700 border-green-200',
          intermediate: 'bg-blue-50 text-blue-700 border-blue-200',
          advanced: 'bg-teal-50 text-teal-700 border-teal-200',
          expert: 'bg-orange-50 text-orange-700 border-orange-200',
        };
    return (
      levels[level?.toLowerCase()] ||
      (isDark
        ? 'bg-gray-900/30 text-gray-300 border-gray-700'
        : 'bg-gray-50 text-gray-700 border-gray-200')
    );
  };

  const getAccentStyles = (accent) => {
    const styles = {
      amber: {
        iconBg: isDark ? 'bg-amber-900/30' : 'bg-amber-100',
        iconText: isDark ? 'text-amber-300' : 'text-amber-700',
        ring: isDark ? 'ring-amber-500/20' : 'ring-amber-200',
        chip: isDark
          ? 'bg-amber-900/30 text-amber-300 border-amber-700'
          : 'bg-amber-50 text-amber-700 border-amber-200',
        bar: isDark ? 'bg-amber-400' : 'bg-amber-500',
      },
      blue: {
        iconBg: isDark ? 'bg-blue-900/30' : 'bg-blue-100',
        iconText: isDark ? 'text-blue-300' : 'text-blue-700',
        ring: isDark ? 'ring-blue-500/20' : 'ring-blue-200',
        chip: isDark
          ? 'bg-blue-900/30 text-blue-300 border-blue-700'
          : 'bg-blue-50 text-blue-700 border-blue-200',
        bar: isDark ? 'bg-blue-400' : 'bg-blue-500',
      },
      cyan: {
        iconBg: isDark ? 'bg-cyan-900/30' : 'bg-cyan-100',
        iconText: isDark ? 'text-cyan-300' : 'text-cyan-700',
        ring: isDark ? 'ring-cyan-500/20' : 'ring-cyan-200',
        chip: isDark
          ? 'bg-cyan-900/30 text-cyan-300 border-cyan-700'
          : 'bg-cyan-50 text-cyan-700 border-cyan-200',
        bar: isDark ? 'bg-cyan-400' : 'bg-cyan-500',
      },
      emerald: {
        iconBg: isDark ? 'bg-emerald-900/30' : 'bg-emerald-100',
        iconText: isDark ? 'text-emerald-300' : 'text-emerald-700',
        ring: isDark ? 'ring-emerald-500/20' : 'ring-emerald-200',
        chip: isDark
          ? 'bg-emerald-900/30 text-emerald-300 border-emerald-700'
          : 'bg-emerald-50 text-emerald-700 border-emerald-200',
        bar: isDark ? 'bg-emerald-400' : 'bg-emerald-500',
      },
      indigo: {
        iconBg: isDark ? 'bg-indigo-900/30' : 'bg-indigo-100',
        iconText: isDark ? 'text-indigo-300' : 'text-indigo-700',
        ring: isDark ? 'ring-indigo-500/20' : 'ring-indigo-200',
        chip: isDark
          ? 'bg-indigo-900/30 text-indigo-300 border-indigo-700'
          : 'bg-indigo-50 text-indigo-700 border-indigo-200',
        bar: isDark ? 'bg-indigo-400' : 'bg-indigo-500',
      },
      orange: {
        iconBg: isDark ? 'bg-orange-900/30' : 'bg-orange-100',
        iconText: isDark ? 'text-orange-300' : 'text-orange-700',
        ring: isDark ? 'ring-orange-500/20' : 'ring-orange-200',
        chip: isDark
          ? 'bg-orange-900/30 text-orange-300 border-orange-700'
          : 'bg-orange-50 text-orange-700 border-orange-200',
        bar: isDark ? 'bg-orange-400' : 'bg-orange-500',
      },
      rose: {
        iconBg: isDark ? 'bg-rose-900/30' : 'bg-rose-100',
        iconText: isDark ? 'text-rose-300' : 'text-rose-700',
        ring: isDark ? 'ring-rose-500/20' : 'ring-rose-200',
        chip: isDark
          ? 'bg-rose-900/30 text-rose-300 border-rose-700'
          : 'bg-rose-50 text-rose-700 border-rose-200',
        bar: isDark ? 'bg-rose-400' : 'bg-rose-500',
      },
      slate: {
        iconBg: isDark ? 'bg-slate-800' : 'bg-slate-100',
        iconText: isDark ? 'text-slate-300' : 'text-slate-700',
        ring: isDark ? 'ring-slate-700' : 'ring-slate-200',
        chip: isDark
          ? 'bg-slate-800 text-slate-300 border-slate-700'
          : 'bg-slate-50 text-slate-700 border-slate-200',
        bar: isDark ? 'bg-slate-400' : 'bg-slate-500',
      },
      violet: {
        iconBg: isDark ? 'bg-violet-900/30' : 'bg-violet-100',
        iconText: isDark ? 'text-violet-300' : 'text-violet-700',
        ring: isDark ? 'ring-violet-500/20' : 'ring-violet-200',
        chip: isDark
          ? 'bg-violet-900/30 text-violet-300 border-violet-700'
          : 'bg-violet-50 text-violet-700 border-violet-200',
        bar: isDark ? 'bg-violet-400' : 'bg-violet-500',
      },
    };
    return styles[accent] || styles.slate;
  };

  const getSkillMeta = (name = '', category = '') => {
    const value = `${name} ${category}`.toLowerCase();

    // Map skills to their logo URLs from CDN
    if (value.includes('java') && !value.includes('javascript')) {
      return {
        accent: 'orange',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
      };
    }
    if (value.includes('spring')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
      };
    }
    if (value.includes('javascript') || value.includes(' js')) {
      return {
        accent: 'amber',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg',
      };
    }
    if (value.includes('typescript') || value.includes('ts')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/typescript/typescript-original.svg',
      };
    }
    if (value.includes('react')) {
      return {
        accent: 'cyan',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg',
      };
    }
    if (value.includes('node')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/nodejs/nodejs-original.svg',
      };
    }
    if (value.includes('python')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
      };
    }
    if (value.includes('docker')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg',
      };
    }
    if (value.includes('kubernetes') || value.includes('k8s')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kubernetes/kubernetes-plain.svg',
      };
    }
    if (value.includes('aws') || value.includes('amazon')) {
      return {
        accent: 'orange',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/amazonwebservices/amazonwebservices-original-wordmark.svg',
      };
    }
    if (value.includes('azure')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/azure/azure-original.svg',
      };
    }
    if (value.includes('postgres')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg',
      };
    }
    if (value.includes('mysql') || value.includes('mariadb')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg',
      };
    }
    if (value.includes('mongo')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg',
      };
    }
    if (value.includes('git') && !value.includes('github')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg',
      };
    }
    if (value.includes('github')) {
      return {
        accent: 'slate',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg',
      };
    }
    if (value.includes('html')) {
      return {
        accent: 'orange',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg',
      };
    }
    if (value.includes('css')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg',
      };
    }
    if (value.includes('tailwind')) {
      return {
        accent: 'cyan',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/tailwindcss/tailwindcss-plain.svg',
      };
    }
    if (value.includes('c#') || value.includes('csharp')) {
      return {
        accent: 'violet',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/csharp/csharp-original.svg',
      };
    }
    if (value.includes('c++') || value.includes('cpp')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg',
      };
    }
    if (value.includes('php')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/php/php-original.svg',
      };
    }
    if (value.includes('ruby')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/ruby/ruby-original.svg',
      };
    }
    if (value.includes('go ') || value === 'go' || value.includes('golang')) {
      return {
        accent: 'cyan',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/go/go-original.svg',
      };
    }
    if (value.includes('angular')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/angularjs/angularjs-original.svg',
      };
    }
    if (value.includes('vue')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg',
      };
    }
    if (value.includes('redis')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/redis/redis-original.svg',
      };
    }
    if (value.includes('nginx')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/nginx/nginx-original.svg',
      };
    }
    if (value.includes('apache')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apache/apache-original.svg',
      };
    }
    if (value.includes('jenkins')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/jenkins/jenkins-original.svg',
      };
    }
    if (value.includes('graphql')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/graphql/graphql-plain.svg',
      };
    }
    if (value.includes('sass') || value.includes('scss')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/sass/sass-original.svg',
      };
    }
    if (value.includes('bootstrap')) {
      return {
        accent: 'violet',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original.svg',
      };
    }
    if (value.includes('webpack')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/webpack/webpack-original.svg',
      };
    }
    if (value.includes('vite')) {
      return {
        accent: 'violet',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vitejs/vitejs-original.svg',
      };
    }
    if (value.includes('npm')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/npm/npm-original-wordmark.svg',
      };
    }
    if (value.includes('yarn')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/yarn/yarn-original.svg',
      };
    }
    if (value.includes('maven')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/maven/maven-original.svg',
      };
    }
    if (value.includes('gradle')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/gradle/gradle-plain.svg',
      };
    }
    if (value.includes('linux')) {
      return {
        accent: 'amber',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/linux/linux-original.svg',
      };
    }
    if (value.includes('ubuntu')) {
      return {
        accent: 'orange',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/ubuntu/ubuntu-plain.svg',
      };
    }
    if (value.includes('debian')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/debian/debian-original.svg',
      };
    }
    if (value.includes('figma')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/figma/figma-original.svg',
      };
    }
    if (value.includes('vscode') || value.includes('visual studio code')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vscode/vscode-original.svg',
      };
    }
    if (value.includes('intellij')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg',
      };
    }
    if (value.includes('sql')) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg',
      };
    }
    if (value.includes('kotlin')) {
      return {
        accent: 'violet',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg',
      };
    }
    if (value.includes('.net') || value.includes('dotnet')) {
      return {
        accent: 'violet',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/dotnetcore/dotnetcore-original.svg',
      };
    }
    if (value.includes('swift')) {
      return {
        accent: 'orange',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/swift/swift-original.svg',
      };
    }
    if (value.includes('fastapi') || value.includes('fast api')) {
      return {
        accent: 'emerald',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/fastapi/fastapi-original.svg',
      };
    }
    if (value.includes('laravel')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/laravel/laravel-original.svg',
      };
    }
    if (value.includes('junit') || value.includes('junit5')) {
      return {
        accent: 'rose',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/junit/junit-original.svg',
      };
    }
    if (value.includes('xcode')) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/xcode/xcode-original.svg',
      };
    }
    if (
      value.includes('distributed systems') ||
      value.includes('distributed')
    ) {
      return {
        accent: 'indigo',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kubernetes/kubernetes-plain.svg',
      };
    }
    if (value.includes('scrum') || value.includes('agile')) {
      return {
        accent: 'emerald',
        logoUrl: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/jira.svg',
      };
    }
    if (value.includes('time management')) {
      return {
        accent: 'cyan',
        logoUrl:
          'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/clockify.svg',
      };
    }
    if (
      value.includes('problem solving') ||
      value.includes('problem-solving')
    ) {
      return {
        accent: 'amber',
        logoUrl:
          'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/electron.svg',
      };
    }
    if (value.includes('teamwork') || value.includes('team work')) {
      return {
        accent: 'indigo',
        logoUrl: 'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/slack.svg',
      };
    }
    if (value.includes('unity')) {
      return {
        accent: 'slate',
        logoUrl:
          'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/unity/unity-original.svg',
      };
    }
    if (
      value.includes('rest') ||
      value.includes('restful') ||
      value.includes('rest api')
    ) {
      return {
        accent: 'blue',
        logoUrl:
          'https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/openapiinitiative.svg',
      };
    }

    // Default fallback
    return {
      accent: 'slate',
      logoUrl:
        'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/devicon/devicon-original.svg',
    };
  };

  const getGridCols = () => {
    return 'grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 max-w-7xl mx-auto';
  };

  const techPatternStyle = {
    backgroundImage: isDark
      ? `linear-gradient(90deg, rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(14, 165, 233, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 18% 22%, rgba(34, 211, 238, 0.28) 0 2px, transparent 2px),
         radial-gradient(circle at 68% 68%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 82% 28%, rgba(56, 189, 248, 0.2) 0 1.5px, transparent 1.5px)`
      : `linear-gradient(90deg, rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         linear-gradient(rgba(37, 99, 235, 0.12) 1px, transparent 1px),
         radial-gradient(circle at 16% 24%, rgba(59, 130, 246, 0.25) 0 2px, transparent 2px),
         radial-gradient(circle at 72% 70%, rgba(14, 165, 233, 0.22) 0 2px, transparent 2px),
         radial-gradient(circle at 84% 26%, rgba(56, 189, 248, 0.18) 0 1.5px, transparent 1.5px)`,
    backgroundSize:
      '80px 80px, 80px 80px, 220px 220px, 260px 260px, 200px 200px',
    backgroundPosition: 'center, center, left top, right bottom, right top',
    maskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
    WebkitMaskImage:
      'radial-gradient(circle at top, rgba(0,0,0,0.9) 0%, rgba(0,0,0,0.35) 55%, transparent 80%)',
  };

  const circuitPatternStyle = {
    backgroundImage: isDark
      ? `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%2322d3ee' stroke-width='1' opacity='0.35'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%2322d3ee' opacity='0.55'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`
      : `url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='140' height='140' viewBox='0 0 140 140'><g fill='none' stroke='%233b82f6' stroke-width='1' opacity='0.3'><path d='M10 18h36v22h32'/><path d='M78 40v30h48'/><path d='M22 92h38v28h46'/><path d='M58 120h28'/></g><g fill='%233b82f6' opacity='0.5'><circle cx='10' cy='18' r='2'/><circle cx='46' cy='40' r='2'/><circle cx='78' cy='40' r='2'/><circle cx='22' cy='92' r='2'/><circle cx='60' cy='120' r='2'/><circle cx='106' cy='120' r='2'/></g></svg>")`,
    backgroundSize: '260px 260px',
    backgroundPosition: 'center',
  };

  const dataStreamStyle = {
    backgroundImage: isDark
      ? `repeating-linear-gradient(120deg, rgba(45, 212, 191, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(59, 130, 246, 0.16) 0 1px, transparent 1px 18px)`
      : `repeating-linear-gradient(120deg, rgba(37, 99, 235, 0.18) 0 1px, transparent 1px 14px),
         repeating-linear-gradient(60deg, rgba(14, 165, 233, 0.16) 0 1px, transparent 1px 18px)`,
    backgroundSize: '100% 100%',
    maskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
    WebkitMaskImage:
      'linear-gradient(180deg, rgba(0,0,0,0.75) 0%, rgba(0,0,0,0.25) 55%, transparent 85%)',
  };

  const glowFieldStyle = {
    backgroundImage: isDark
      ? `radial-gradient(circle at 12% 18%, rgba(56, 189, 248, 0.35), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(99, 102, 241, 0.28), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(14, 165, 233, 0.25), transparent 50%)`
      : `radial-gradient(circle at 12% 18%, rgba(59, 130, 246, 0.25), transparent 45%),
         radial-gradient(circle at 78% 32%, rgba(14, 165, 233, 0.22), transparent 48%),
         radial-gradient(circle at 48% 80%, rgba(99, 102, 241, 0.18), transparent 50%)`,
  };

  if (loading) {
    return (
      <div
        className={`min-h-screen flex items-center justify-center transition-colors duration-200 ${
          isDark
            ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
            : 'bg-gradient-to-br from-blue-50 via-white to-cyan-50'
        }`}
      >
        <div className="text-center">
          <div
            className={`inline-block animate-spin rounded-full h-12 w-12 border-b-2 ${
              isDark ? 'border-blue-400' : 'border-blue-600'
            }`}
          ></div>
          <p className={`mt-4 ${isDark ? 'text-slate-400' : 'text-slate-600'}`}>
            {t('loading')}
          </p>
        </div>
      </div>
    );
  }

  return (
    <div
      className={`min-h-screen py-12 transition-colors duration-200 relative overflow-hidden ${
        isDark
          ? 'bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950'
          : 'bg-gradient-to-br from-blue-50 via-white to-cyan-50'
      }`}
    >
      <div className="pointer-events-none absolute inset-0" aria-hidden="true">
        <div
          className={`absolute -top-20 -left-20 h-72 w-72 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-blue-700' : 'bg-blue-300'
          }`}
        ></div>
        <div
          className={`absolute -bottom-24 right-0 h-96 w-96 rounded-full blur-3xl opacity-30 ${
            isDark ? 'bg-cyan-700' : 'bg-cyan-300'
          }`}
        ></div>
      </div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-60 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={techPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-35 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={circuitPatternStyle}
      ></div>
      <div
        className={`pointer-events-none absolute inset-0 opacity-40 ${
          isDark ? 'mix-blend-screen' : 'mix-blend-multiply'
        }`}
        aria-hidden="true"
        style={dataStreamStyle}
      ></div>
      <div
        className="pointer-events-none absolute inset-0 opacity-60"
        aria-hidden="true"
        style={glowFieldStyle}
      ></div>
      <div className="max-w-6xl mx-auto px-4 relative z-10">
        <div className="text-center mb-12">
          <span
            className={`inline-flex items-center px-4 py-1.5 rounded-full text-sm font-semibold border mb-4 ${
              isDark
                ? 'bg-slate-800/60 border-slate-700 text-slate-200'
                : 'bg-slate-50/70 border-slate-200 text-slate-700'
            }`}
          >
            Tech Stack
          </span>
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-cyan-600 mb-4">
            {t('skills')}
          </h1>
          <p
            className={`text-lg max-w-2xl mx-auto ${
              isDark ? 'text-slate-400' : 'text-slate-600'
            }`}
          >
            {t('discoverTechnologiesTools')}
          </p>
        </div>

        {error && (
          <div
            className={`max-w-2xl mx-auto mb-6 border px-4 py-3 rounded-lg ${
              isDark
                ? 'bg-red-900/30 border-red-700 text-red-300'
                : 'bg-red-50 border-red-200 text-red-700'
            }`}
          >
            {t('errorOccurred')}
          </div>
        )}

        <div className={getGridCols()}>
          {skills.map((skill) => {
            const displayName =
              currentLang === 'es' && skill.nameEs
                ? skill.nameEs
                : currentLang === 'fr' && skill.nameFr
                  ? skill.nameFr
                  : skill.nameEn;
            const meta = getSkillMeta(displayName, skill.category);
            const accent = getAccentStyles(meta.accent);

            return (
              <div
                key={skill.id}
                className={`group relative rounded-3xl p-8 transition-all duration-300 ring-1 hover:-translate-y-3 hover:shadow-2xl ${
                  isDark
                    ? 'bg-slate-900/70 ring-slate-800 hover:ring-slate-700'
                    : 'bg-slate-50/90 ring-slate-200 hover:ring-slate-300'
                }`}
              >
                <div className="absolute inset-0 rounded-3xl opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                  <div
                    className={`absolute inset-0 rounded-3xl bg-gradient-to-br ${
                      isDark
                        ? 'from-blue-500/5 via-transparent to-cyan-500/5'
                        : 'from-blue-500/10 via-transparent to-cyan-500/10'
                    }`}
                  ></div>
                </div>

                <div className="relative flex flex-col items-center text-center">
                  <div
                    className={`mb-6 w-20 h-20 rounded-2xl flex items-center justify-center transition-transform duration-300 group-hover:scale-110 ${
                      isDark ? 'bg-slate-800' : 'bg-slate-50'
                    }`}
                  >
                    <img
                      src={meta.logoUrl}
                      alt={`${displayName} logo`}
                      className="w-12 h-12 object-contain"
                      loading="lazy"
                      onError={(e) => {
                        e.target.style.display = 'none';
                      }}
                    />
                  </div>

                  <h2
                    className={`text-xl font-bold mb-3 transition ${
                      isDark ? 'text-slate-100' : 'text-slate-900'
                    }`}
                  >
                    {displayName}
                  </h2>

                  <div className="flex flex-wrap items-center justify-center gap-2">
                    <span
                      className={`text-xs px-3 py-1.5 rounded-full border font-medium ${accent.chip}`}
                    >
                      {skill.category || 'Technology'}
                    </span>
                    <span
                      className={`text-xs px-3 py-1.5 rounded-full border font-medium ${getLevelColor(skill.proficiencyLevel)}`}
                    >
                      {skill.proficiencyLevel}
                    </span>
                  </div>
                </div>
              </div>
            );
          })}
        </div>

        {skills.length === 0 && !loading && (
          <div className="text-center py-12">
            <p
              className={`text-lg ${
                isDark ? 'text-slate-400' : 'text-slate-600'
              }`}
            >
              {t('noSkillsAvailable')}
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
