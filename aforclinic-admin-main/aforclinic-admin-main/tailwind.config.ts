import type { Config } from 'tailwindcss';
import typography from '@tailwindcss/typography';

export default {
  content: [
    './app/components/**/*.vue',
    './app/layouts/**/*.vue',
    './app/pages/**/*.vue',
    './app/app.vue',
  ],
  theme: {
    extend: {
      colors: {
        primary: `var(--q-primary)`,
        secondary: `var(--q-secondary)`,
      },
    },
  },
  plugins: [
    typography,
  ],
} as Config;
