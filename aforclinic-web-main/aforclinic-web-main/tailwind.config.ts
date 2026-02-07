import type { Config } from 'tailwindcss';
import typography from '@tailwindcss/typography';
import scrollbarHide from 'tailwind-scrollbar-hide';

export default {
  content: [
    './app/**/*.vue',
  ],
  theme: {
    extend: {
      colors: {
        primary: '#f9fafb',
      },
      fontFamily: {
        serif: ['EB Garamond', 'Georgia', 'serif'],
      },
    },
  },
  plugins: [
    typography,
    scrollbarHide,
  ],
} as Config;
