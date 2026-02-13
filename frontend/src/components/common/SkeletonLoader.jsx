import React from 'react';
import { useTheme } from '../../context/ThemeContext';

/**
 * SkeletonLoader - Reusable skeleton placeholder component
 * Shows animated loading placeholder to prevent "flash" of 0 values
 *
 * @param {React.ReactNode} children - Content to display (rendered when not loading)
 * @param {boolean} isLoading - Whether to show skeleton or content
 * @param {string} className - Additional CSS classes
 * @param {string} variant - Skeleton variant: 'stat', 'card', 'text', 'line'
 */
export const SkeletonLoader = ({
  children,
  isLoading = false,
  className = '',
  variant = 'stat',
}) => {
  const { isDark } = useTheme();

  if (!isLoading) {
    return children;
  }

  const baseClasses = `animate-pulse ${className}`;

  const variantClasses = {
    stat: `h-full rounded-lg`,
    card: `h-48 rounded-xl`,
    text: `h-4 rounded`,
    line: `h-6 rounded`,
  };

  const bgColor = isDark ? 'bg-slate-800' : 'bg-slate-200';

  return (
    <div
      className={`${baseClasses} ${variantClasses[variant] || variantClasses.stat} ${bgColor}`}
      aria-busy="true"
      aria-label="Loading content"
    />
  );
};

/**
 * StatSkeletonPlaceholder - Specialized skeleton for stat cards
 * Shows icon, value, and label placeholders
 */
export const StatSkeletonPlaceholder = () => {
  const { isDark } = useTheme();
  const bgColor = isDark ? 'bg-slate-800' : 'bg-slate-200';

  return (
    <div className="text-center">
      {/* Icon placeholder */}
      <div
        className={`w-12 h-12 mx-auto mb-3 rounded-lg ${bgColor} animate-pulse`}
      ></div>

      {/* Value placeholder */}
      <div
        className={`w-16 h-10 mx-auto mb-2 rounded-lg ${bgColor} animate-pulse`}
      ></div>

      {/* Label placeholder */}
      <div
        className={`w-32 h-4 mx-auto rounded-lg ${bgColor} animate-pulse`}
      ></div>
    </div>
  );
};

export default SkeletonLoader;
