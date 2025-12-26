import antfu from '@antfu/eslint-config';
import tailwindcss from 'eslint-plugin-tailwindcss';
import nuxt from './.nuxt/eslint.config.mjs';

export default nuxt(
  antfu({
    formatters: true,
    stylistic: {
      indent: 2, // 4, or 'tab'
      quotes: 'single', // or 'double'
      semi: true,
    },
    plugins: {
      tailwindcss,
    },
    rules: {
      'curly': ['error', 'all'],
      'no-console': 'off',
      'no-restricted-syntax': [
        'error',
        {
          selector: 'CallExpression[callee.object.name=\'console\'][callee.property.name!=/^(log|warn|error|info|trace)$/]',
          message: 'Unexpected property on console object was called',
        },
      ],
      'style/max-statements-per-line': ['error', { max: 2 }],
      'unused-imports/no-unused-imports': ['error'],
      'vue/max-attributes-per-line': ['error', {
        singleline: { max: 1 },
        multiline: { max: 1 },
      }],
      'vue/no-multiple-template-root': ['off'],
      'tailwindcss/classnames-order': 'error',
    },
  }),
);
