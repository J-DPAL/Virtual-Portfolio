import { AnimatePresence, motion } from 'framer-motion';
import { prefersReducedMotion } from '../../utils/motionUtils';

export const PageTransitionWrapper = ({ children, location }) => {
  const reducedMotion = prefersReducedMotion();

  return (
    <AnimatePresence mode="wait">
      <motion.div
        key={location.pathname}
        initial={reducedMotion ? false : { opacity: 0, y: 24 }}
        animate={reducedMotion ? false : { opacity: 1, y: 0 }}
        exit={reducedMotion ? false : { opacity: 0, y: -24 }}
        transition={
          reducedMotion
            ? { duration: 0 }
            : { duration: 0.35, ease: 'easeInOut' }
        }
        style={{ minHeight: '100%' }}
        aria-label="Page transition"
      >
        {children}
      </motion.div>
    </AnimatePresence>
  );
};
